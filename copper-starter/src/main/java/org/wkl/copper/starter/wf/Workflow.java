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

package org.wkl.copper.starter.wf;


import org.copperengine.core.Interrupt;
import org.copperengine.core.WaitMode;
import org.copperengine.core.WorkflowDescription;
import org.copperengine.core.persistent.PersistentWorkflow;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wkl.copper.starter.data.WorkflowData;

@WorkflowDescription(alias = "RegistrationWorkflow", majorVersion = 1, minorVersion = 0, patchLevelVersion = 0)
public class Workflow extends PersistentWorkflow<WorkflowData> {

    private static final long serialVersionUID = 1L;

    private static final Logger logger = LoggerFactory.getLogger(org.wkl.copper.starter.wf.Workflow.class);

    @Override
    public void main() throws Interrupt {
        logger.info("Dummy account registration workflow started for personId: {}, workflow instance: {}", getData().getPersonId(), getId());
        sleep(10);
        logger.info("Dummy account registration workflow finished for personId: {}, workflow instance: {}", getData().getPersonId(),getId());
    }

    private void sleep(int seconds) throws Interrupt {
        logger.info("Waiting for {} seconds, doing nothing at all ", seconds);
        wait(WaitMode.ALL, seconds * 1000, getEngine().createUUID());
    }

}
