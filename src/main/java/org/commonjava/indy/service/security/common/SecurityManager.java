package org.commonjava.indy.service.security.common;

import io.quarkus.security.identity.SecurityIdentity;
import org.jboss.resteasy.spi.HttpRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.security.Principal;
import java.util.Set;

@ApplicationScoped
public class SecurityManager
{
    private final Logger logger = LoggerFactory.getLogger( this.getClass() );

    @Inject
    SecurityConfiguration config;

    @Inject
    SecurityIdentity identity;

    @Inject
    SecurityBindings bindings;

    public boolean authorized( final String path, final String httpMethod )
    {
        Set<String> roles = identity.getRoles();
        logger.debug( "Roles {}", roles );
        if ( config.enabled() && bindings != null )
        {
            for ( SecurityConstraint constraint : bindings.getConstraints() )
            {
                final boolean pathMatched = path.matches( constraint.getUrlPattern() );
                final boolean methodMatched = constraint.getMethods().contains( httpMethod );
                logger.debug( "path: {}, constraint url pattern: {}, path match: {}, method: {}, method match {}", path,
                              constraint.getUrlPattern(), pathMatched, httpMethod, methodMatched );
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
                        logger.debug( "Role {} is not allowed to access path {} through method {}", roles, path,
                                      httpMethod );
                        return false;
                    }
                }
            }
        }

        logger.debug( "No security bindings found or matched, so no security limitation." );
        return true;
    }

    public String getUser( HttpRequest request )
    {
        if ( !config.enabled() )
        {
            return request.getRemoteHost();
        }

        if ( identity == null )
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