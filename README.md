# Indy Security setttings

This lib will provide a solution to do authorization in indy microservices.

## Prerequisite for building
1. jdk11
2. mvn 3.6.2+

## Configure
There are two basic config items:
* enabled: decide if to enable the security checking
* security_bindings_yaml: decide where is the security-bindings.yaml file

A sample quarkus configuration file is here: [application.yaml](src/test/resources/application.yaml) 

## security-bindings.yaml
This lib uses security-bindings.yaml to decide the security constraints. A sample file is here [security-bindings.yaml](src/test/resources/security-bindings.yaml)

The lib will load this file from two locations:
* classpath: this is default location if second one is not specified
* security_bindings_yaml in config: a file path to specify the location.