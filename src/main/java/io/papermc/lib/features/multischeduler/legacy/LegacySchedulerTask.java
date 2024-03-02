package io.papermc.lib.features.multischeduler.legacy;

import io.papermc.lib.features.multischeduler.models.SchedulerTask;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.NotNull;

public class LegacySchedulerTask implements SchedulerTask {

    private final BukkitTask task;

    public LegacySchedulerTask(BukkitTask bukkitRunnable) {
        this.task = bukkitRunnable;
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return this.task.getOwner();
    }

    @Override
    public boolean isCancelled() {
        return this.task.isCancelled();
    }

    @Override
    public void cancel() {
        this.task.cancel();
    }

    @Override
    public int getTaskId() {
        return this.task.getTaskId();
    }
}
