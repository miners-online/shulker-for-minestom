package uk.minersonline.shulker_for_minestom.configs;

import java.io.*;
import java.util.Properties;

public class ServerProperties {
	private static final String CONFIG_FILE = "server.properties";
	private static final Properties properties = new Properties();

	public static void load() {
		// Step 1: Check if the config file exists
		File configFile = new File(CONFIG_FILE);
		if (!configFile.exists()) {
			System.out.println("Configuration file not found. Creating default configuration.");

			// Step 2: Set default properties
			properties.setProperty("level-name", "world");
			properties.setProperty("shulker-agent-disabled", "false");
			properties.setProperty("server-ip", "0.0.0.0");
			properties.setProperty("server-port", "25565");
			properties.setProperty("online-mode", "true");


			// Step 3: Save default properties to the file
			try (OutputStream output = new FileOutputStream(CONFIG_FILE)) {
				properties.store(output, "Shulker for Minestom Server config");
			} catch (IOException io) {
				io.printStackTrace();
			}

		} else {
			// If the config file exists, load properties from the file
			try (InputStream input = new FileInputStream(CONFIG_FILE)) {
				properties.load(input);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static Properties getProperties() {
		return properties;
	}
}
