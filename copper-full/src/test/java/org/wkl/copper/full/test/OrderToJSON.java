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

package org.wkl.copper.full.test;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.wkl.copper.full.data.Item;
import org.wkl.copper.full.data.Order;

import java.io.IOException;

public class OrderToJSON {

    public static void main(String[] args) {

        ObjectMapper objectMapper = new ObjectMapper();
        Order order = new Order(74747474747L, "57575757", 7277226662626233L);
        Item item = new Item();
        item.setId(415);
        item.setQuantity(1);
        item.setUnitPrice(12.3);
        item.setDescription("64 MByte SD Card");
        order.getItems().add(item);
        order.setCredit_card("7277226662626234");
        try {
            objectMapper.writeValue(System.out,order);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }
}
