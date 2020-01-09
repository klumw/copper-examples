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

import org.copperengine.core.ProcessingEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URL;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

public class OutputAdapter {

    public static final String BASE_URL = "http://mock:8080/mock/api";

    private static final Logger logger = LoggerFactory.getLogger(OutputAdapter.class);

    private ProcessingEngine engine;

    public String asyncCheckCreditCard(String creditCardNumber) throws Exception {
        String correlationId = createCorrelationId();

        String url = BASE_URL + "/creditcards/" + creditCardNumber;
        logger.info("Sending credit card check request: {}", url);
        Response resp = ClientBuilder.newClient()
                .target(
                        URI.create(new URL(url).toExternalForm()))
                .request(TEXT_PLAIN).header("X-Correlation-Id", correlationId).get();//send correlation id as header param
        if (resp.getStatus() == 400) {
            throw new NumberFormatException("Invalid credit card number format:" + creditCardNumber);
        }
        return correlationId;
    }

    public boolean checkAccountId(String accountId) throws Exception {
        String url = BASE_URL + "/accounts/" + accountId;
        logger.info("Sending account id check request: {}", url);
        Response resp = ClientBuilder.newClient()
                .target(
                        URI.create(new URL(url).toExternalForm()))
                .request(TEXT_PLAIN).get();
        if (resp.getStatus() == 400) {
            throw new NumberFormatException("Invalid  account id format:" + accountId);
        }
        if (resp.getStatus() == 404) {
            return false;
        }
        if (resp.getStatus() == 200) {
            return true;
        }
        throw new Exception("Unexpected status code: " + resp.getStatus() + " in check account id response.");
    }

    public void setEngine(ProcessingEngine engine) {
        this.engine = engine;
    }

    private String createCorrelationId() {
        return "CORRELATION-ID-" + engine.createUUID();
    }

}
