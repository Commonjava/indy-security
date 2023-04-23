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

import io.quarkus.security.identity.SecurityIdentity;
import org.jboss.resteasy.spi.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.security.Principal;
import java.util.Collections;
import java.util.Set;

@ApplicationScoped
public class SecurityManager
{
    private final Logger logger = LoggerFactory.getLogger( this.getClass() );

    @Inject
    SecurityConfiguration config;

    @Inject
    SecurityIdentity identity;

//    @Inject
//    @IdToken
//    JsonWebToken idToken;

    @Inject
    SecurityBindings bindings;

    public boolean authorized( final String path, final String httpMethod )
    {
        if ( config.enabled() && bindings != null )
        {
            Set<String> roles = getRoles();
            Principal user = identity.getPrincipal();
            if ( user != null )
            {
                logger.debug( "User: {}", user.getName() );
            }
            logger.debug( "Roles {}", roles );
            for ( SecurityConstraint constraint : bindings.getConstraints() )
            {
                final boolean pathMatched = path.matches( constraint.getUrlPattern() );
                final boolean methodMatched = constraint.getMethods().contains( httpMethod );
                logger.debug( "path: {}, constraint url pattern: {}, path match: {}, method: {}, method match: {}",
                              path, constraint.getUrlPattern(), pathMatched, httpMethod, methodMatched );
                if ( pathMatched && methodMatched )
                {
                    if ( roles != null && !roles.isEmpty() && roles.contains( constraint.getRole() ) )
                    {
                        logger.debug( "Role {} is allowed to access path {} through method {}", roles, path,
                                      httpMethod );
                        return true;
                    }
                    else
                    {
                        logger.warn( "Role {} is not allowed to access path {} through method {}", roles, path,
                                     httpMethod );
                        return false;
                    }
                }
            }
        }

        logger.debug( "No security bindings found or matched, so no security limitation." );
        return true;
    }

    public Set<String> getRoles()
    {
        Set<String> roles = identity.getRoles();
        return ( roles != null && !roles.isEmpty() ) ? roles : Collections.emptySet();
    }

    @SuppressWarnings( "unused" )
    public String getUser( HttpRequest request )
    {
        if ( !config.enabled() || identity == null )
        {
            return request.getRemoteHost();
        }

        Principal userPrincipal = identity.getPrincipal();
        if ( userPrincipal == null )
        {
            return request.getRemoteHost();
        }

        String user = userPrincipal.getName();
        return user == null ? request.getRemoteHost() : user;
    }
}