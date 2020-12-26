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

import org.copperengine.core.CopperException;
import org.copperengine.core.ProcessingEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.wkl.copper.starter.data.WorkflowData;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

@Path("/customer")
public class InputAdapter {

    private static final Logger logger = LoggerFactory.getLogger(org.wkl.copper.starter.adapter.InputAdapter.class);

    private ProcessingEngine engine;

    private static final String WF = "org.wkl.copper.starter.wf.Workflow";

    @Autowired
    public void setEngine(ProcessingEngine engine) {
        this.engine = engine;
    }


    @GET
    public Response test(){
        return Response.ok().build();
    }

    @GET
    @Path("/accounts/{personid}/{age}")
    public Response createWorkflow(@PathParam("personid") String personId, @PathParam("age") int age) {

        WorkflowData data = createCopperWorkflow(personId, age);
        if (data != null) {
            return Response.ok().entity(data).build();
        } else {
            return Response.serverError().build();
        }

    }

    private WorkflowData createCopperWorkflow(String personid, int age) {
        WorkflowData data = new WorkflowData(personid, age);
        try {
            engine.run(WF, data);
            return data;
        } catch (CopperException e) {
            logger.error("OrderCheckWorkflow creation failed", e);
            return null;
        }
    }

}
