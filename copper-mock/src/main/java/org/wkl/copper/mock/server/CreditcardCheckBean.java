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

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.*;
import javax.ws.rs.client.ClientBuilder;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

import static javax.ws.rs.core.MediaType.TEXT_PLAIN;

@Singleton
@LocalBean
@Startup
public class CreditcardCheckBean {

    private static final Logger logger = LoggerFactory.getLogger(org.wkl.copper.mock.server.CreditcardCheckBean.class);

    private Queue<QueuedCardRequest> cardRequestQueue = new LinkedList<QueuedCardRequest>();

    public static final String BASE_URL = "http://copper:8080/copper/rest";

    List<String> validCards = Arrays.asList(new String[]{"7277226662626234", "7277226662626235", "7277226662626237", "7277226662626238", "7277226662626239", "7277226662626240"});

    @Resource

    private TimerService timerService;

    @PostConstruct
    private void init() {
        timerService.createTimer(10000, 10000, "IntervalTimerCreditCardRequest");
    }

    @Timeout
    public void execute(Timer timer) {
        while (cardRequestQueue.size() > 0) {
            QueuedCardRequest request = cardRequestQueue.remove();
            logger.debug("Got request=" + request);

            String correlationId = request.getRequestId();
            String creditCardId = request.getCredit_card();
            if (creditCardId != null) {
                boolean isValid = validCards.contains(creditCardId);
                try {
                    logger.info("Sending completion request for {}", request);
                    ClientBuilder.newClient()
                            .target(
                                    URI.create(new URL(BASE_URL + "/orders/creditcards/" + request.getCredit_card() + "/completion/" + isValid).toExternalForm()))
                            .request(TEXT_PLAIN).header("X-Correlation-Id", correlationId).get();
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                }
            } else {
                logger.error("Credit card check bean. CreditCard id value is null!");
            }
        }
    }

    public void addCreditCardRequest(QueuedCardRequest request) {
        cardRequestQueue.add(request);
    }
}
