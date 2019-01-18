# kotlin-smartapp ![kotlin-logo](../../docs/kotlin-logo.png) ![ktor-logo](../../docs/ktor-logo.png)

This sample demonstrates a simple [Kotlin](https://kotlinlang.org/)-based server using [Ktor](https://ktor.io) as a web server.

## Requirements

### Required to build

* Java 8 or later
* A Kotlin-friendly IDE, like [JetBrains IntelliJ IDEA](https://www.jetbrains.com/idea/).
  * The Kotlin plugin for IntelliJ (`version >= 1.3.11`)
* The ability to successfully compile the `smartapp-core` & `smartthings-client` projects.

### Required to execute

* Samsung account
* App created on the [developer workspace](https://devworkspace.developer.samsung.com/smartthingsconsole/iotweb/site/index.html)

## Building

From the root project directory:

./gradlew examples:kotlin-smartapp:compileJava

## Running

From the root project directory:

./gradlew examples:kotlin-smartapp:run
