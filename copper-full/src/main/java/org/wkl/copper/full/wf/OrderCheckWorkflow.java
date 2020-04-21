/*
 * Copyright 2020 Winfried Klum
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.wkl.copper.full.wf;


import org.copperengine.core.*;
import org.copperengine.core.audit.AuditTrail;
import org.copperengine.core.audit.AuditTrailEvent;
import org.copperengine.core.persistent.PersistentWorkflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wkl.copper.full.adapter.OutputAdapter;
import org.wkl.copper.full.data.Order;

import java.util.Date;
import java.util.concurrent.TimeUnit;

/**
 * Example order workflow that validates account id and credit card number.
 */
@WorkflowDescription(alias = "OrderCheckWorkflow", majorVersion = 1, minorVersion = 0, patchLevelVersion = 0)
public class OrderCheckWorkflow extends PersistentWorkflow<Order> {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(OrderCheckWorkflow.class);

    private transient OutputAdapter outputAdapter;

    private transient AuditTrail auditTrail;

    @AutoWire
    public void setOutputAdapter(OutputAdapter outputAdapter) {
        this.outputAdapter = outputAdapter;
    }

    @AutoWire
    public void setAuditTrail(AuditTrail auditTrail) {
        this.auditTrail = auditTrail;
    }

    @Override
    public void main() throws Interrupt {
        logger.info("OrderCheckWorkflow started.\n -Workflow instance: {}\n -{}", this.getId(), getData().toString());
        boolean isOrderValid = true;
        setState("OrderIdCheck");
        checkOrderId();
        setState("AccountIdCheck");
        if (checkAccountId() == false) {
            isOrderValid = false;
        }
        if (getData().getCredit_card() != null) {
            setState("CreditCardCheck");
            if (checkCreditCard() == false) {
                isOrderValid = false;
            }
        }
        logger.info("Order Check {} for:\n -{}\n -Workflow instance: {}", isOrderValid ? "succeeded" : "failed", getData().toString(), this.getId());
        setState("Finished");
    }

    //set workflow to ERROR on invalid order id 
    private void checkOrderId() {
        if (getData().getOrderId() <= 0) {
            logger.info("Order id is invalid.\n -Next we throw a Runtime Exception and the workflow instance is set to ERROR.\n -See also Copper GUI -> broken workflows.");
            throw new RuntimeException("Invalid order id: " + getData().getOrderId() + ", order ids must be > 0");
        }
    }

    //synchronous account id check
    private boolean checkAccountId() {

        String accountId = this.getData().getAccountId();
        try {
            boolean isValid = outputAdapter.checkAccountId(accountId);
            if (!isValid) {
                logger.info("Account id is not valid for {}", getData().toString());
                return false;
            }
        } catch (NumberFormatException ne) {
            logger.info("account id error:", ne);
            return false;
        } catch (Exception e) {
            logger.error("Internal error in account id check", e);
            auditTrail.synchLog(new AuditTrailEvent(1, new Date(), "Order-Id: " + this.getData().getOrderId(), "AccountIdCheck", this.getId(), null, null, "No response for account id request, account id: " + getData().getAccountId(), ""));
            throw new RuntimeException(e);
        }
        return true;
    }

    //asynchronous credit card number check
    private boolean checkCreditCard() throws Interrupt {

        String orderCorrelationId = null;
        try {
            orderCorrelationId = outputAdapter.asyncCheckCreditCard(getData().getCredit_card());
        } catch (NumberFormatException ne) {
            //we got status 400 (invalid credit card number format)
            logger.info("Credit card number error:", ne);
            return false;
        } catch (Exception e) {
            logger.error("Internal error in credit card check", e);
            auditTrail.synchLog(new AuditTrailEvent(1, new Date(), "Order-Id: " + this.getData().getOrderId(), "CreditCardCheck", this.getId(), orderCorrelationId, null, "No response for credit card check request, credit card number:" + getData().getCredit_card(), ""));
            throw new RuntimeException(e);
        }
        // wait asynchronously for the credit card check result
        wait(WaitMode.ALL, 60, TimeUnit.SECONDS, orderCorrelationId);

        // retrieve credit card check result
        Response<Boolean> creditcardCheckResponse = getAndRemoveResponse(orderCorrelationId);
        //we always get a response object, but Response itself might be null
        if (creditcardCheckResponse.getResponse() == null) {
            logger.error("No credit card check response for {}", getData().toString());
            auditTrail.synchLog(new AuditTrailEvent(1, new Date(), "Order-Id: " + this.getData().getOrderId(), "CreditCardCheck", this.getId(), orderCorrelationId, null, "No response for credit card check request, credit card number:" + getData().getCredit_card(), ""));
            throw new RuntimeException("No credit card check response received");
        }
        //get credit card check result
        boolean isCreditCardValid = creditcardCheckResponse.getResponse();
        if (!isCreditCardValid) {
            logger.info("invalid credit card for {}, see also Copper GUI -> AuditTrails.", getData().toString());
            auditTrail.synchLog(new AuditTrailEvent(1, new Date(), "Order-Id: " + this.getData().getOrderId(), "CreditCardCheck", this.getId(), null, null, "Credit card number: " + getData().getCredit_card() + " not valid", ""));
            return false;
        } else {
            logger.info("Credit card {} is valid.", this.getData().getCredit_card());
        }
        return true;
    }

    /**
     * Set workflow instance state and persist data object.
     * If data is persisted in JSON format (see MixedModeSerializer)
     * the workflow instance data can be searched on database level (see also PostgreSQL JSON)
     * @param state
     * @throws Interrupt
     */
    private void setState(String state) throws Interrupt {
        getData().setState(state);
        savepoint();
    }

}
