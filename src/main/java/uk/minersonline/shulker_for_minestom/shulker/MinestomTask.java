package uk.minersonline.shulker_for_minestom.shulker;

import io.shulkermc.serveragent.ServerInterface;
import net.minestom.server.timer.Task;

public class MinestomTask implements ServerInterface.ScheduledTask{
	private final Task task;

	public MinestomTask(Task task) {
		this.task = task;
	}

	@Override
	public void cancel() {
		task.cancel();
	}
}
