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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecurityConstraintTest
{
    private final ObjectMapper mapper = new ObjectMapper( new YAMLFactory() ).findAndRegisterModules();

    @Test
    public void testDeSerilizationArrayType()
            throws Exception
    {
        assertContraints( "security-bindings.yaml" );
    }

    @Test
    public void testDeSerilizationStringType()
            throws Exception
    {
        assertContraints( "security-bindings-string.yaml" );
    }

    @Test
    public void testDeSerilizationHyrbidType()
            throws Exception
    {
        assertContraints( "security-bindings-hybrid.yaml" );
    }

    @Test
    public void testDeSerilizationHyrbidType2()
            throws Exception
    {
        assertContraints( "security-bindings-hybrid2.yaml" );
    }

    @Test
    public void testDeSerilizationOld()
            throws Exception
    {
        assertContraints( "security-bindings-old.yaml" );
    }

    private void assertContraints( final String inputFile )
            throws IOException
    {
        try (InputStream input = this.getClass().getClassLoader().getResourceAsStream( inputFile ))
        {
            SecurityBindings constraintSet = mapper.readValue( input, SecurityBindings.class );
            if ( constraintSet != null )
            {
                List<SecurityConstraint> constraints = constraintSet.getConstraints();
                assertThat( constraints.size(), is( 3 ) );
                for ( SecurityConstraint constraint : constraints )
                {
                    switch ( constraint.getUrlPattern() )
                    {
                        case "/api/admin/.*":
                            assertContraintValues( constraint.getMethods(), 3, "POST", "PUT", "DELETE" );
                            assertContraintValues( constraint.getRoles(), 1, "admin" );
                            break;
                        case "/api/.*":
                            assertContraintValues( constraint.getMethods(), 3, "POST", "PUT", "DELETE" );
                            assertContraintValues( constraint.getRoles(), 1, "user" );
                            break;
                        case "/api/admin/stores/.*":
                            assertContraintValues( constraint.getMethods(), 3, "POST", "PUT", "DELETE" );
                            assertContraintValues( constraint.getRoles(), 2, "admin", "power-user" );
                            break;
                        default:
                            Assertions.fail(
                                    String.format( "%s pattern should not exist!", constraint.getUrlPattern() ) );
                    }
                }
            }
        }
    }

    private void assertContraintValues( List<String> list, int expectSize, String... expectedContents )
    {
        assertThat( list.size(), is( expectSize ) );
        for ( String content : expectedContents )
        {
            assertTrue( list.contains( content ) );
        }
    }

}
