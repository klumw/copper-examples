
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

package org.wkl.copper.full.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Order implements Serializable {

    private List<Item> items = new ArrayList<Item>();

    private long customerId;

    private String credit_card;

    private String accountId;

    private long orderId;

    public Order() {
    }

    public Order(long customerId, String accountId, long orderId) {
        this.customerId = customerId;
        this.accountId = accountId;
        this.orderId = orderId;
    }

    public Order(long customerId, String accountId, long orderId, String credit_card) {
        this.customerId = customerId;
        this.accountId = accountId;
        this.orderId = orderId;
        this.credit_card = credit_card;
    }

    public List<Item> getItems() {
        return items;
    }

    public void setItems(List<Item> items) {
        this.items = items;
    }

    public long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(long customerId) {
        this.customerId = customerId;
    }

    public String getCredit_card() {
        return credit_card;
    }

    public void setCredit_card(String credit_card) {
        this.credit_card = credit_card;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return getCustomerId() == order.getCustomerId() &&
                getOrderId() == order.getOrderId() &&
                getItems().equals(order.getItems()) &&
                Objects.equals(getCredit_card(), order.getCredit_card()) &&
                getAccountId().equals(order.getAccountId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getItems(), getCustomerId(), getCredit_card(), getAccountId(), getOrderId());
    }

    @Override
    public String toString() {
        return "Order{" +
                "items=" + items +
                ", customerId=" + customerId +
                ", credit_card='" + credit_card + '\'' +
                ", accountId='" + accountId + '\'' +
                ", orderId=" + orderId +
                '}';
    }
}
