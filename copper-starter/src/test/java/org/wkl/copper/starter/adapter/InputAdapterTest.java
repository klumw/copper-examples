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

package org.wkl.copper.starter.adapter;

import org.copperengine.core.ProcessingEngine;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.glassfish.jersey.test.TestProperties;
import org.junit.Test;
import org.mockito.Mock;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.Mockito.*;

public class InputAdapterTest extends JerseyTest {


    @Path("test")
    public static class RequestingResource {


        @GET
        public Response get() {
            return  Response.ok().build();
        }
    }


   // @Mock
   // ProcessingEngine engine;

    @Override
    protected Application configure() {
        System.out.println("------------------------------------------------------------");
        var cfg = new ResourceConfig(RequestingResource.class);

        //InputAdapter adapter = new InputAdapter();
        //adapter.setEngine(engine);
        //cfg.register(RequestingResource.class);
        enable(TestProperties.LOG_TRAFFIC);
        enable(TestProperties.DUMP_ENTITY);
        System.out.println(cfg.getConfiguration().getResources().toString());
        System.out.println("<-------------------------------------------------------------->");
        //cfg.register(adapter);
        Map<String,Object> props = new HashMap<>();
        //props.put("engine",engine);
        props.put("jersey.config.server.provider.packages", "org.wkl.copper.starter.adapter");
        cfg.setProperties(props);
        //cfg.setApplicationName("test");
        //System.out.println("ApplicationName:" + cfg.getApplicationName());
        return cfg;
    }

    @Test
    public void testCreateWorkflow(){
        System.out.println(target("test").getUri().toString());
        Response output = target("test").request().get();
    }

}
