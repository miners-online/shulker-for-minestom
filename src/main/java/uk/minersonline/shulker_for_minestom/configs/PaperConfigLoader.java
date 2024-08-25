package uk.minersonline.shulker_for_minestom.configs;

import org.yaml.snakeyaml.DumperOptions;
import org.yaml.snakeyaml.Yaml;
import org.yaml.snakeyaml.nodes.Tag;
import org.yaml.snakeyaml.representer.Representer;

import java.io.*;


public class PaperConfigLoader {
	public static PaperConfig loadConfig() {
		String filePath = "config/paper-global.yml";
		File configFile = new File(filePath);

		try {
			// Check if the config file exists
			if (!configFile.exists()) {
				// Create default config if the file does not exist
				createDefaultConfig(configFile);
			}

			// Load the YAML file from the filesystem
			InputStream inputStream = new FileInputStream(configFile);

			// Create Yaml instance for dumping with appropriate options and representer
			DumperOptions options = new DumperOptions();
			options.setIndent(2);
			options.setPrettyFlow(true);
			options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

			// Set up a Representer to omit type tags
			Representer representer = new Representer(options);
			representer.getPropertyUtils().setSkipMissingProperties(true);
			representer.addClassTag(PaperConfig.class, Tag.MAP);

			Yaml yaml = new Yaml(representer, options);

			// Parse the YAML file into Config object
			return yaml.loadAs(inputStream, PaperConfig.class);

		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	private static void createDefaultConfig(File configFile) throws IOException {
		// Ensure the parent directories exist
		File parentDir = configFile.getParentFile();
		if (parentDir != null && !parentDir.exists()) {
			parentDir.mkdirs();
		}

		// Create default configuration data
		PaperConfig defaultConfig = new PaperConfig();
		PaperConfig.ProxyConfig velocityConfig = new PaperConfig.ProxyConfig();
		velocityConfig.setEnabled(false);
		velocityConfig.setSecret("default_secret_value");

		PaperConfig.Proxies proxies = new PaperConfig.Proxies();
		proxies.setVelocity(velocityConfig);

		defaultConfig.setProxies(proxies);

		// Create Yaml instance for dumping with appropriate options and representer
		DumperOptions options = new DumperOptions();
		options.setIndent(2);
		options.setPrettyFlow(true);
		options.setDefaultFlowStyle(DumperOptions.FlowStyle.BLOCK);

		// Set up a Representer to omit type tags
		Representer representer = new Representer(options);
		representer.getPropertyUtils().setSkipMissingProperties(true);
		representer.addClassTag(PaperConfig.class, Tag.MAP);

		Yaml yaml = new Yaml(representer, options);

		// Write default config to file using a Writer
		try (Writer writer = new OutputStreamWriter(new FileOutputStream(configFile))) {
			yaml.dump(defaultConfig, writer);
			System.out.println("Default config created at: " + configFile.getAbsolutePath());
		}
	}
}
