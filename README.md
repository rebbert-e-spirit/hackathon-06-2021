# Crownpeak Hackathon 2021: Micro-Hack Integration

This repository is used to provide a basic setup for the CXT Platform.
Using Docker images, it is very simple to create a test environment
with only a few commands.

## Overview

```
+------------------------------------------+  +------------------+
|                                          |  |                  |
| +------------------+  +----------------+ |  |                  |
| |  localhost:8080  |  | localhost:8666 | |  | localhost:8200   |
| |                  |  |                | |  |                  |
| |   CXT Platform   |  | Demo-MicroApp  | |  | example-client   |
| +------------------+  +----------------+ |  |                  |
|                                          |  |                  |
|                   Docker                 |  | ./example-client |
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

Starting the CXT Platform as well as the Demo-MicroApp
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

Please verify that the MicroApp is registered with the platform.
This may take up to one minute after both services have been started.

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
