# Shulker for Minestom

A Shulker Agent implementation for the Minestom Minecraft server.

> [!IMPORTANT]
> Shulker for Minestom only supports the Velocity proxy, however it would be simple to include Bungeecord support.

## Getting Shulker for Minestom

### 1. Clone this repo

`git clone https://github.com/miners-online/shulker-for-minestom.git`

### 2. Compile the jar

`./gradlew shadowJar`

Once complete, this will output a `Shulker for Minestom-1.0-SNAPSHOT-all.jar` file inside the `build/libs` directory.

## Using Shulker for Minestom

1. Import the previous jar
2. Create the following test server:

```java
import uk.minersonline.shulker_for_minestom.ShulkerBootstrap;

public class TestServer {
	public static void main(String[] args) {
		ShulkerBootstrap.init();
		// Do whatever you need here, e.g. listen for the `AsyncPlayerConfigurationEvent` event.
		ShulkerBootstrap.start();
	}
}

```

3. Modify it to add your own functionality

## How it works.

Shulker for Minestom *"pretends"* to be a standard Paper server. It does this by:

- reading standard `server.properties` and `paper-global.yml` files. Thees files are essential for a standard Paper server to work behind a proxy.
- behaving the same way to fields inside those files as a standard Paper server.
- implementing a custom Shulker Agent, so Shulker is aware of the Minestom server.
