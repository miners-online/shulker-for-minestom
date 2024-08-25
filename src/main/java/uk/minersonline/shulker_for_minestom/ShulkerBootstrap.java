package uk.minersonline.shulker_for_minestom;

import net.minestom.server.MinecraftServer;
import net.minestom.server.extras.MojangAuth;
import net.minestom.server.extras.velocity.VelocityProxy;
import uk.minersonline.shulker_for_minestom.configs.PaperConfig;
import uk.minersonline.shulker_for_minestom.configs.PaperConfigLoader;
import uk.minersonline.shulker_for_minestom.configs.ServerProperties;
import uk.minersonline.shulker_for_minestom.shulker.ShulkerServerAgentMinestom;

import java.util.Properties;
import java.util.logging.Logger;

public class ShulkerBootstrap {
	private static Properties serverProperties;
	private static PaperConfig config;
	private static MinecraftServer server;

	public static void init() {
		// Loads all the standard config files of a normal Paper server.
		ServerProperties.load();
		serverProperties = ServerProperties.getProperties();
		config = PaperConfigLoader.loadConfig();

		server = MinecraftServer.init();

		// Enable or disable Mojang auth based on the `server.properties` online-mode field.
		if (serverProperties.getProperty("online-mode") != null && serverProperties.getProperty("online-mode").equalsIgnoreCase("true")) {
			MojangAuth.init();
		}

		// Enable velocity authentication based on the settings of a standard `paper-global.yml` file.
		if (config != null) {
			if (config.getProxies().getVelocity().isEnabled()) {
				VelocityProxy.enable(config.getProxies().getVelocity().getSecret());
			}
		}

		// Allow the user to choose if they want Shulker to be enabled.
		if (serverProperties.getProperty("shulker-agent-disabled") == null || !serverProperties.getProperty("shulker-agent-disabled").equalsIgnoreCase("true")) {
			ShulkerServerAgentMinestom.init(Logger.getLogger("Shulker Agent"));
		}
	}

	public static void start() {
		// Start the Minestom server using the IP and port from the `server.properties` file.
		server.start(serverProperties.getProperty("server-ip"), Integer.parseInt(serverProperties.getProperty("server-port")));
	}

	public static Properties getServerProperties() {
		return serverProperties;
	}

	public static PaperConfig getConfig() {
		return config;
	}
}
