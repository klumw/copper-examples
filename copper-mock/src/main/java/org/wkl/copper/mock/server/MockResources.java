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

package org.wkl.copper.mock.server;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wkl.copper.mock.data.QueuedCardRequest;

import javax.ejb.EJB;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;


@Path("api")
public class MockResources {

    private static final Logger logger = LoggerFactory.getLogger(org.wkl.copper.mock.server.MockResources.class);

    @EJB
    CreditcardCheckBean creditcardCheckBean;

    @GET
    @Path("/creditcards/{id}")
    public Response validateCreditCard(@PathParam("id") String cardNumber, @Context HttpHeaders httpheaders) {
        String correlationId = httpheaders.getHeaderString("X-Correlation-Id");
        logger.info("validateCreditCard called with credit card number: {}, Correlation Id: {}", cardNumber, correlationId);
        try {
            Long.valueOf(cardNumber);
            if (cardNumber.length() != 16) {
                throw new NumberFormatException("Invalid card number format. Actual digits=" + cardNumber.length() + ", expected 16.");
            }
            creditcardCheckBean.addCreditCardRequest(new QueuedCardRequest(cardNumber, correlationId));
            return Response.status(200).build();
        } catch (NumberFormatException ne) {
            logger.info("Invalid credit card number format:", ne);
            return Response.status(400).build();
        }
    }

    @GET
    @Path("/accounts/{id}")
    public Response validateAccountId(@PathParam("id") String accountId) {
        logger.info("Validating account id");
        try {
            long id = Long.valueOf(accountId);
            if (id < 60000000 && id > 0) {
                return Response.status(200).build();
            } else {
                return Response.status(404).build();
            }
        } catch (NumberFormatException ne) {
            return Response.status(400).build();
        }
    }

}
