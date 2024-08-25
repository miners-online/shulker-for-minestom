package uk.minersonline.shulker_for_minestom.shulker;

import io.shulkermc.serveragent.ShulkerServerAgentCommon;
import net.minestom.server.MinecraftServer;

import java.util.logging.Logger;

public class ShulkerServerAgentMinestom {
	private static ShulkerServerAgentMinestom instance;
	private final ShulkerServerAgentCommon agent;

	private ShulkerServerAgentMinestom(Logger logger) {
		this.agent = new ShulkerServerAgentCommon(new ServerInterfaceMinestom(), logger);
	}

	public static void init(Logger logger) {
		if (instance == null) {
			instance = new ShulkerServerAgentMinestom(logger);
		}
		instance.agent.onServerInitialization();
		MinecraftServer.getSchedulerManager().buildShutdownTask(instance.agent::onServerShutdown);
	}
}
