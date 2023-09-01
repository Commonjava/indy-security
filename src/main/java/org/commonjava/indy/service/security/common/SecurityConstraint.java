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

import java.util.Arrays;
import java.util.List;

@SuppressWarnings( "unused" )
public class SecurityConstraint
{

    private String urlPattern;

    private List<String> roles;

    private List<String> methods;

    public SecurityConstraint( String urlPattern, List<String> roles, List<String> methods )
    {
        super();
        this.urlPattern = urlPattern;
        this.roles = roles;
        this.methods = methods;
    }

    public SecurityConstraint( String urlPattern, String[] roles, String[] methods )
    {
        this( urlPattern, Arrays.asList( roles ), Arrays.asList( methods ) );
    }

    public SecurityConstraint()
    {
        // keep default constructor
    }

    public List<String> getRoles()
    {
        return roles;
    }

    public void setRole( List<String> roles )
    {
        this.roles = roles;
    }

    public String getUrlPattern()
    {
        return urlPattern;
    }

    public void setUrlPattern( String urlPattern )
    {
        this.urlPattern = urlPattern;
    }

    public List<String> getMethods()
    {
        return methods;
    }

    public void setMethods( List<String> methods )
    {
        this.methods = methods;
    }

    @Override
    public String toString()
    {
        return "SecurityConstraint [role=" + roles + ", urlPattern=" + urlPattern + ", methods=" + methods + "]";
    }

}
