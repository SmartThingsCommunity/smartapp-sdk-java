# Java Spring Boot Example SmartApp ![java-logo](../../docs/java-logo.png) ![spring-logo](../../docs/spring-logo.png)

This sample demonstrates a simple [Java](https://www.oracle.com/java/) server using
[Spring Boot](https://spring.io).

## Requirements

* Java 1.8+
* SmartThings developer account

## Running the Example

### Build and Run

Execute the following command from the project root directory:

```
./gradlew examples:java-springboot-smartapp:bootRun
```

### Create SmartApp

Note: it is necessary for the application to be running before you create the SmartApp
in the developer console.

In the [developer workspace](https://devworkspace.developer.samsung.com/smartthingsconsole/iotweb/site/index.html),
choose "Automations" on the left navigation bar and create a new WebHook endpoint automation using the following
settings:

| Option             | Value                               |
|--------------------|-------------------------------------|
| SmartApp Instances | Single                              |
| Hosting Type       | WebHook endpoint                    |
| Target URL         | Enter the URL of your endpoint here |
| Scopes             | `r:devices:*` and `x:devices:*`     |

Once created, self-publish the automation for testing and you will be ready to test it.

### Configure Public Key

Copy the public key created in the previous step into a file called `smartthings_rsa.pub` in `src/main/resources`
and estart your server (see [Build and Run](#build-and-run) above).

### Install SmartApp

First, be sure to enable
[developer mode](https://smartthings.developer.samsung.com/docs/guides/testing/developer-mode.html#Enable-Developer-Mode)
in your SmartThings application to see the self-published automation.

Now you should see your SmartApp listed (near the bottom) when you add a new Automation.

## More about SmartThings

If you are not familiar with SmartThings, we have
[extensive on-line documentation](https://smartthings.developer.samsung.com/develop/index.html).

To create and manage your services and devices on SmartThings, create an account in the
[developer workspace](https://devworkspace.developer.samsung.com/).

The [SmartThings Community](https://community.smartthings.com/c/developers/) is a good place share and
ask questions.

There is also a [SmartThings reddit community](https://www.reddit.com/r/SmartThings/) where you
can read and share information.

## License and Copyright

Licensed under the [Apache License, Version 2.0](https://www.apache.org/licenses/LICENSE-2.0)

Copyright 2019 SmartThings, Inc.
