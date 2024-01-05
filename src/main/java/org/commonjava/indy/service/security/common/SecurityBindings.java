/**
 * Copyright (C) 2022 Red Hat, Inc. (https://github.com/Commonjava/indy-security)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.commonjava.indy.service.security.common;

import jakarta.enterprise.inject.Alternative;
import jakarta.inject.Named;
import java.util.List;

@Alternative
@Named
public class SecurityBindings
{

    private List<SecurityConstraint> constraints;

    public List<SecurityConstraint> getConstraints()
    {
        return constraints;
    }

    public void setConstraints( final List<SecurityConstraint> securityContraints )
    {
        this.constraints = securityContraints;
    }

}

