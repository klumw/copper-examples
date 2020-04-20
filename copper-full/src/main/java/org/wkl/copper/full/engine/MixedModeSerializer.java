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

package org.wkl.copper.full.engine;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;
import com.fasterxml.jackson.databind.jsontype.PolymorphicTypeValidator;
import org.copperengine.core.Workflow;
import org.copperengine.core.persistent.SerializedWorkflow;
import org.copperengine.core.persistent.StandardJavaSerializer;

import java.io.IOException;

/**
 * Mixed Mode Serializer with standard java serialization for workflow instance and default JSON
 * serialization for workflow data object
 */

public class MixedModeSerializer extends StandardJavaSerializer {

    private ObjectMapper mapper;

    private boolean useJson;

    private Class baseType;

    public MixedModeSerializer() {
        this(true);
    }

    public MixedModeSerializer(boolean useJson) {
        this.useJson = useJson;
        if (useJson) {
            baseType = Object.class;
            initJsonMapper();
        }
    }

    public MixedModeSerializer(Class baseType) {
        this.useJson = true;
        this.baseType = baseType;
        initJsonMapper();
    }

    private void initJsonMapper() {
        PolymorphicTypeValidator ptv = BasicPolymorphicTypeValidator.builder()
                .allowIfBaseType(baseType).build();
        mapper = JsonMapper.builder().activateDefaultTyping(ptv, ObjectMapper.DefaultTyping.NON_FINAL).build();
    }

    protected String serializeData(Workflow<?> o) throws IOException {
        if (useJson) {
            return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(o.getData());
        } else {
            return super.serializeData(o);
        }
    }

    protected Object deserializeData(SerializedWorkflow sw) throws Exception {
        if (useJson) {
            return mapper.readValue(sw.getData(), baseType);
        } else {
            return super.deserializeData(sw);
        }

    }


}
