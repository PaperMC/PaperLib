package io.papermc.lib.features.multischeduler.folia;

import io.papermc.lib.features.multischeduler.models.SchedulerTask;
import io.papermc.paper.threadedregions.scheduler.ScheduledTask;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public class FoliaSchedulerTask implements SchedulerTask {

    private final ScheduledTask task;

    public FoliaSchedulerTask(ScheduledTask bukkitRunnable) {
        this.task = bukkitRunnable;
    }

    @Override
    public @NotNull Plugin getPlugin() {
        return this.task.getOwningPlugin();
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
        return this.task.hashCode();
    }
}
