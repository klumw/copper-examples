
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
import java.util.Objects;

public class Item implements Serializable {

    private int quantity;
    private long id;
    private String description;
    private double unitPrice;

    public Item() {
    }

    public Item(long id, String description, int quantity, double unitPrice) {
        this.id = id;
        this.description = description;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(double unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return getQuantity() == item.getQuantity() &&
                getId() == item.getId() &&
                Double.compare(item.getUnitPrice(), getUnitPrice()) == 0 &&
                getDescription().equals(item.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getQuantity(), getId(), getDescription(), getUnitPrice());
    }

    @Override
    public String toString() {
        return "Item{" +
                "quantity=" + quantity +
                ", id=" + id +
                ", description='" + description + '\'' +
                ", unitPrice=" + unitPrice +
                '}';
    }
}
