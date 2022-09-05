/**
 * Copyright (C) 2020 Red Hat, Inc.
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
package org.commonjava.indy.service.security.jaxrs;

import javax.enterprise.context.ApplicationScoped;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

import static javax.ws.rs.core.Response.Status.CREATED;
import static javax.ws.rs.core.Response.Status.NO_CONTENT;

@Path( "/api" )
@ApplicationScoped
public class TestResource
{
    @Path( "/public" )
    @GET
    public Response getPublic()
    {
        return Response.ok().build();
    }

    @Path( "/admin/resource" )
    @PUT
    public Response putAdmin()
    {
        return Response.status( CREATED ).build();
    }

    @Path( "/admin/resource" )
    @POST
    public Response postAdmin()
    {
        return Response.status( CREATED ).build();
    }

    @Path( "/admin/resource" )
    @DELETE
    public Response deleteAdmin()
    {
        return Response.status( NO_CONTENT ).build();
    }

    @Path( "/admin/stores/{type}/{name}" )
    @PUT
    public Response putStores()
    {
        return Response.status( CREATED ).build();
    }

    @Path( "/admin/stores/{type}/{name}" )
    @POST
    public Response postStores()
    {
        return Response.status( CREATED ).build();
    }

    @Path( "/admin/stores/{type}/{name}" )
    @DELETE
    public Response deleteStores()
    {
        return Response.status( NO_CONTENT ).build();
    }

    @Path( "/admin/stores/{type}/{name}" )
    @GET
    public Response getStores()
    {
        return Response.ok().build();
    }
}
