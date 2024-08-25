package uk.minersonline.shulker_for_minestom.shulker;

import io.shulkermc.serveragent.ServerInterface;
import io.shulkermc.serveragent.platform.HookPostOrder;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import net.minestom.server.MinecraftServer;
import net.minestom.server.event.Event;
import net.minestom.server.event.EventNode;
import net.minestom.server.event.player.PlayerDisconnectEvent;
import net.minestom.server.event.player.PlayerSpawnEvent;
import net.minestom.server.permission.Permission;
import net.minestom.server.timer.TaskSchedule;
import org.jetbrains.annotations.NotNull;

import java.time.Duration;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class ServerInterfaceMinestom implements ServerInterface {
	private final EventNode<Event> eventNode = EventNode.all("shulker-server-agent-minestom");

	public ServerInterfaceMinestom() {
		var handler = MinecraftServer.getGlobalEventHandler();
		handler.addChild(eventNode);
	}

	@Override
	public void addPlayerJoinHook(@NotNull Function0<Unit> hook, @NotNull HookPostOrder hookPostOrder) {
		eventNode.addListener(PlayerSpawnEvent.class, event -> hook.invoke());
	}

	@Override
	public void addPlayerQuitHook(@NotNull Function0<Unit> hook, @NotNull HookPostOrder hookPostOrder) {
		eventNode.addListener(PlayerDisconnectEvent.class, event -> hook.invoke());
	}

	@Override
	public int getPlayerCount() {
		return MinecraftServer.getConnectionManager().getOnlinePlayers().size();
	}

	@Override
	public void prepareNetworkAdminsPermissions(@NotNull List<UUID> list) {
		eventNode.addListener(PlayerSpawnEvent.class, (event -> {
			if (list.contains(event.getPlayer().getUuid())) {
				event.getPlayer().setPermissionLevel(4);
				event.getPlayer().addPermission(new Permission("*"));
			}
		}));
	}

	@NotNull
	@Override
	public ScheduledTask scheduleDelayedTask(
			long delay,
			TimeUnit timeUnit,
			@NotNull Runnable runnable
		) {

		Duration duration = Duration.ofNanos(timeUnit.toNanos(delay));
		var task = MinecraftServer.getSchedulerManager().scheduleTask(
				runnable,
				TaskSchedule.duration(duration),
				TaskSchedule.stop()
		);
		return new MinestomTask(task);
	}

	@NotNull
	@Override
	public ScheduledTask scheduleRepeatingTask(
			long delay,
			long interval,
			TimeUnit timeUnit,
			@NotNull Runnable runnable
		) {

		Duration delayDuration = Duration.ofNanos(timeUnit.toNanos(delay));
		Duration intervalDuration = Duration.ofNanos(timeUnit.toNanos(interval));
		var task = MinecraftServer.getSchedulerManager().scheduleTask(
				runnable,
				TaskSchedule.duration(delayDuration),
				TaskSchedule.duration(intervalDuration)
		);
		return new MinestomTask(task);
	}
}
