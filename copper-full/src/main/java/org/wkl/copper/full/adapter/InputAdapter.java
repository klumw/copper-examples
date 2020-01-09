
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

package org.wkl.copper.full.adapter;

import org.copperengine.core.Acknowledge;
import org.copperengine.core.CopperException;
import org.copperengine.core.ProcessingEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.wkl.copper.full.data.Item;
import org.wkl.copper.full.data.Order;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/orders")
public class InputAdapter {

    private static final Logger logger = LoggerFactory.getLogger(InputAdapter.class);

    private ProcessingEngine engine;

    private static final String WF = "org.wkl.copper.full.wf.OrderCheckWorkflow";

    @Autowired
    public void setEngine(ProcessingEngine engine) {
        this.engine = engine;
    }

    @POST
    @Path("/submit")
    public Response createWorkflow(@RequestBody Order order) {
        try {
            engine.run(WF, order);
            return Response.ok().build();
        } catch (CopperException e) {
            logger.error("Unable to create workflow instance", e);
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/test")
    public Response runTestData() {
        List<Order> orders = getTestData();
        try {
            for (Order order : orders) {
                engine.run(WF, order);
            }
            return Response.ok().entity("Copper tests launched.").build();
            //return Response.ok().build();
        } catch (CopperException e) {
            return Response.serverError().build();
        }
    }

    @GET
    @Path("/creditcards/{id}/completion/{valid}")
    public Response creditCardCheckCompletion(@PathParam("id") String creditcard_number, @PathParam("valid") String valid, @HeaderParam("X-Correlation-Id") String correlationId) {
        logger.info("Received completion request for credit card number: {}, workflow correlation id: {}", creditcard_number, correlationId);
        Boolean isValid = Boolean.valueOf(valid);
        org.copperengine.core.Response copperResp = new org.copperengine.core.Response<>(correlationId, isValid, null);
        Acknowledge.DefaultAcknowledge ack = new Acknowledge.DefaultAcknowledge();
        engine.notify(copperResp, ack);
        ack.waitForAcknowledge();
        return Response.ok().build();
    }

    private List<Order> getTestData() {
        long accountId = 59999997;
        long customerId = 3300033;
        long creditCardNumber = 7277226662626234L;
        List<Item> itemList = new ArrayList<>();
        itemList.add(new Item(123, "MYK-24242", 2, 4.65));
        itemList.add(new Item(453, "EAK-322", 3, 8.32));
        itemList.add(new Item(786, "ABB-929-we", 1, 32.95));
        List<Order> orderList = new ArrayList<>();
        int maxItems = 1;
        for (long orderId = 0; orderId < 5; orderId++) {
            Order order = new Order(customerId++, Long.valueOf(accountId++).toString(), orderId, Long.valueOf(creditCardNumber++).toString());
            for (int i = 0; i < maxItems; i++) {
                order.getItems().add(itemList.get(i));
            }
            maxItems++;
            if (maxItems >= orderList.size()) {
                maxItems = 1;
            }
            orderList.add(order);
        }
        return orderList;
    }
}
