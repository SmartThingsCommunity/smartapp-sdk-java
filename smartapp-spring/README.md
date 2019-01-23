# smartapp-spring

This is a simple library to make declaring Smart App lifecycle handlers as Spring components easy.

## Requirements

* Java 1.8+
* SmartThings developer account
* Spring 4+
* [smartapp-core](/smartapp-core)

## Installation

More information on this topic is coming when the first release is published to a repository.

## Usage

This simple library adds to smartapp-core by giving you the ability to create a `SmartAppDefinition`
using handlers defined as components. To do this, you simply call `SpringSmartAppDefinition.of`
and give it your `ApplicationContext`.

```java
SmartAppDefinition smartAppDefinition = SpringSmartAppDefinition.of(applicationContext);
```

For a complete example, see [java-spring example smart app](/examples/java-spring-smartapp).
