# Crownpeak Hackathon 2021: Micro-Hack Integration

This repository is used to provide a basic setup for the CXT Platform.
Using Docker images, it is very simple to create a test environment
with only a few commands.

## Overview

```
+------------------------------------------+  +------------------+
|                                          |  |                  |
| +------------------+  +----------------+ |  |                  |
| |  localhost:8100  |  | localhost:8080 | |  | localhost:8200   |
| |                  |  |                | |  |                  |
| |     Keycloak     |  |  CXT Platform  | |  | example-client   |
| +------------------+  +----------------+ |  |                  |
|                                          |  |                  |
|                       +----------------+ |  |                  |
|                       | localhost:8666 | |  | ./example-client |
|       Docker          |                | |  |                  |
|                       | Demo-MicroApp  | |  |                  |
|                       +----------------+ |  |                  |
|                                          |  |                  |
+------------------------------------------+  +------------------+
```

 * First fire up the CXT Platform as described below.
 * Then you can write your own CXT client that integrates the demo MicroApp. [example-client/README.md](./example-client/README.md)

## Docker Registry Setup

For this Hackathon we created a temporary Docker registry.
Just login with the provided credentials:

```shell
$ docker login cp-es-hackathon.artifactory.e-spirit.de
```

## Starting the CXT Platform

Starting the CXT Platform as well as the required Keycloak server
is done by using a `docker-compose.yml` file. After cloning this repository,
just run `docker-compose up`:

```shell
$ docker-compose up
```

If you are curious and want to look behind the curtain, feel free to examine the
YAML file. It declares two docker containers and their configuration.

## Checking the Status Page

When both services started successfully, open the status page in your browser:

[http://localhost:8080/cxt-platform/status](http://localhost:8080/cxt-platform/status)

Viewing the status page requires admin access, so you will be redirected to
Keycloak for login. Please use these credentials:

```
Username: cxt-admin
Password: admin
```

When the platform is started initially, there are no MicroApps registered.

## Starting the MicroApp Demo

The MicroApp-Demo is shipped as another Docker image. You need to provide information
about how to connect to the CXT Platform when starting the image:

```shell
docker run -it --network="host" \
        -e SPRING_APPLICATION_JSON='{
                "server.port":"8666",
                "eureka.client.enabled":"true",
                "eureka.client.serviceUrl.defaultZone":"http://cxt_eureka:GEHEIM@localhost:8080/cxt-platform/eureka",
                "eureka.instance.metadata-map.internalUrl":"http://localhost:8666/",
                "eureka.instance.homePageUrl":"http://localhost:8666/",
                "cxt.firstspirit-connector.url":"http://localhost:8080/cxt-platform/firstspirit-connector/"
        }' \
        cp-es-hackathon.artifactory.e-spirit.de/cxt/microapp-demo:1.5-fs5.2.201209
```

After that, the MicroApp is available in standalone mode: http://localhost:8666/

Wait a few seconds (up to one minute) and check the status page again: http://localhost:8080/cxt-platform/status

Please observe that the MicroApp is now registered with the platform.

## REST: Get all available MicroApps

Let's start with the integration. You can fetch all available MicroApps via REST.

```shell
$ curl http://localhost:8080/cxt-platform/microapps/components/
["cxt-micro-app-demo"]
```

## REST: Retrieve all MicroApp buttons matching a given context 

The MicroApp registry returns available buttons matching a context in JSON format.
In this demonstration, we provide a simple empty context `{}`. Since the MicroApp demo
is configured to support all contexts, its button descriptor is returned.

```shell
$ curl -X POST --location "http://localhost:8080/cxt-platform/microapps/buttons/" -d "{}"    
[{"accessToken":"",
  "title":"Show 'Hello World' MicroApp",
  "microAppId":"cxt-micro-app-demo",
  "endpointUrl":"http://localhost:8666/",
  "iconUrl":"http://localhost:8666/icon.svg"}]
```

## REST: Fetch the MicroApp configuration

```shell
$ curl http://localhost:8080/cxt-platform/microapps/components/cxt-micro-app-demo/configuration/
{"startUri":"http://localhost:8666/api/handle-start",
 "buttonUri":"http://localhost:8666/api/button",
 "endpointUri":"http://localhost:8666/",
 "contextSupportUri":"http://localhost:8666/api/supports-context",
 "userInterfaceUri":"http://localhost:8666/",
 "resultUri":"http://localhost:8666/api/handle-result",
 "name":"cxt-micro-app-demo"}
```
