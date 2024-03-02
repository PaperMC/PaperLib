package io.papermc.lib.features.multischeduler.models;

import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

public interface SchedulerTask {
    @NotNull
    Plugin getPlugin();

    boolean isCancelled();

    void cancel();

    int getTaskId();
}
