/**
 * Copyright (C) 2023 Red Hat, Inc.
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

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.fasterxml.jackson.dataformat.yaml.JacksonYAMLParseException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static com.fasterxml.jackson.databind.node.JsonNodeType.STRING;

public class SecurityConstraintDeSerializer
        extends StdDeserializer<SecurityConstraint>
{
    public SecurityConstraintDeSerializer()
    {
        this( null );
    }

    public SecurityConstraintDeSerializer( Class<?> vc )
    {
        super( vc );
    }

    @Override
    public SecurityConstraint deserialize( JsonParser p, DeserializationContext ctxt )
            throws IOException, JacksonException
    {
        JsonNode node = p.getCodec().readTree( p );
        JsonNode rolesNode = node.get( "roles" );
        if ( rolesNode == null )
        {
            rolesNode = node.get( "role" );
            if ( rolesNode == null )
            {
                throw new JacksonYAMLParseException( p, "\"roles\" should not be empty!", null );
            }
        }
        List<String> roles = handleVariableNode( p, rolesNode );

        JsonNode urlPatNode = node.get( "urlPattern" );
        if ( urlPatNode == null )
        {
            throw new JacksonYAMLParseException( p, "\"urlPattern\" should not be empty!", null );
        }
        final String urlPattern;
        if ( urlPatNode.getNodeType() == STRING )
        {
            urlPattern = urlPatNode.textValue();
        }
        else
        {
            throw new JacksonYAMLParseException( p, "\"urlPattern\" should be string type!", null );
        }

        JsonNode methodsNode = node.get( "methods" );
        if ( methodsNode == null )
        {
            throw new JacksonYAMLParseException( p, "\"methods\" should not be empty!", null );
        }
        List<String> methods = handleVariableNode( p, methodsNode );

        return new SecurityConstraint( urlPattern, roles, methods );
    }

    private List<String> handleVariableNode( JsonParser p, JsonNode node )
            throws JacksonException
    {
        List<String> result = new ArrayList<>();
        switch ( node.getNodeType() )
        {
            case ARRAY:
                for ( JsonNode child : node )
                {
                    if ( child.getNodeType() == STRING )
                    {
                        final String value = child.textValue();
                        result.add( value );
                    }
                }
                break;
            case STRING:
                final String[] resultStr = node.textValue().split( "," );
                for ( String rt : resultStr )
                {
                    result.add( rt.trim() );
                }
                break;
            default:
                throw new JacksonYAMLParseException( p, "\"roles\" should be string or list type!", null );
        }
        return result;
    }
}
