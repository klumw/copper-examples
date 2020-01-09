
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

package org.wkl.copper.mock.data;

public class QueuedCardRequest {
    private String credit_card;
    private String requestId;

    public QueuedCardRequest(String credit_card, String requestId) {
        this.credit_card = credit_card;
        this.requestId = requestId;
    }

    public String getCredit_card() {
        return credit_card;
    }

    public String getRequestId() {
        return requestId;
    }

    @Override
    public String toString() {
        return "QueuedCardRequest{" +
                "credit_card='" + credit_card + '\'' +
                ", requestId='" + requestId + '\'' +
                '}';
    }
}
