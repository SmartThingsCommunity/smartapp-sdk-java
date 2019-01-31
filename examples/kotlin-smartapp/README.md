# kotlin-smartapp ![kotlin-logo](../../docs/kotlin-logo.png) ![ktor-logo](../../docs/ktor-logo.png)

This sample demonstrates a simple [Kotlin](https://kotlinlang.org/)-based server using [Ktor](https://ktor.io) as a web server.

* Java 1.8+
* SmartThings developer account

## Running the Example

From the root project directory:

```
./gradlew examples:kotlin-smartapp:compileJava
./gradlew examples:kotlin-smartapp:run
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
