package org.commonjava.indy.service.security.jaxrs;

import org.commonjava.indy.service.security.common.SecurityManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
import javax.ws.rs.ext.Provider;
import java.io.IOException;

@ApplicationScoped
@Provider
public class SecurityInterceptor
        implements ContainerRequestFilter
{
    private final Logger logger = LoggerFactory.getLogger( this.getClass() );

    @Inject
    SecurityManager securityManager;

    @Context
    UriInfo info;

    @Override
    public void filter( ContainerRequestContext requestContext )
            throws IOException
    {

        final String path = info.getPath();

        final String method = requestContext.getMethod();

        if ( !securityManager.authorized( path, method ) )
        {
            requestContext.abortWith( Response.status( Response.Status.FORBIDDEN ).build() );
        }
    }
}
