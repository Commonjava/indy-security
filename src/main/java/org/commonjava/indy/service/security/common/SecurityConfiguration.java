package org.commonjava.indy.service.security.common;

import io.quarkus.runtime.Startup;
import io.smallrye.config.ConfigMapping;
import io.smallrye.config.WithDefault;
import io.smallrye.config.WithName;

import javax.enterprise.context.ApplicationScoped;
import java.util.Optional;

@Startup
@ConfigMapping( prefix = "indy_security" )
@ApplicationScoped
public interface SecurityConfiguration
{
    @WithName( "enabled" )
    @WithDefault( "false" )
    Boolean enabled();

    @WithName( "security_bindings_yaml" )
    Optional<String> securityBindingsYaml();
}
