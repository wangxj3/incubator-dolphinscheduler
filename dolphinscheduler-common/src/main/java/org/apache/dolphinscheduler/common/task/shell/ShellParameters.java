/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.apache.dolphinscheduler.common.task.shell;

import org.apache.dolphinscheduler.common.process.Property;
import org.apache.dolphinscheduler.common.process.ResourceInfo;
import org.apache.dolphinscheduler.common.task.AbstractParameters;
import org.apache.dolphinscheduler.common.utils.CollectionUtils;
import org.apache.dolphinscheduler.common.utils.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * shell parameters
 */
public class ShellParameters extends AbstractParameters {
    /**
     * shell script
     */
    private String rawScript;

    /**
     * resource list
     */
    private List<ResourceInfo> resourceList;

    public String getRawScript() {
        return rawScript;
    }

    public void setRawScript(String rawScript) {
        this.rawScript = rawScript;
    }

    public List<ResourceInfo> getResourceList() {
        return resourceList;
    }

    public void setResourceList(List<ResourceInfo> resourceList) {
        this.resourceList = resourceList;
    }

    @Override
    public boolean checkParameters() {
        return rawScript != null && !rawScript.isEmpty();
    }

    @Override
    public List<ResourceInfo> getResourceFilesList() {
        return resourceList;
    }

    @Override
    public void dealOutParam(String result) {
        if (StringUtils.isEmpty(result)) {
            return;
        }
        if (CollectionUtils.isEmpty(localParams)) {
            return;
        }
        List<Property> outProperty = getOutProperty(localParams);
        if (CollectionUtils.isEmpty(outProperty)) {
            return;
        }
        Map<String, String> taskResult = getMapByString(result);
        if (taskResult == null || taskResult.size() == 0) {
            return;
        }
        for (Property info : outProperty) {
            info.setValue(taskResult.get(info.getProp()));
            varPool.add(info);
        }
    }

}
