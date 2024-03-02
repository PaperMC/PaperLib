package io.papermc.lib.features.multischeduler.models;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public interface Scheduler {

    void runAtFixedRate(@NotNull SchedulerType schedulerType, SchedulerCallBack callBack, long initialDelayTicks, long periodTicks);

    void runAtFixedRate(@NotNull SchedulerType schedulerType, World world, int chunkX, int chunkZ, SchedulerCallBack callBack, long initialDelayTicks, long periodTicks);

    void runAtFixedRate(@NotNull SchedulerType schedulerType, Location location, SchedulerCallBack callBack, long initialDelayTicks, long periodTicks);

    void runAtFixedRate(@NotNull SchedulerType schedulerType, Entity entity, SchedulerCallBack callBack, @Nullable Runnable retired, long initialDelayTicks, long periodTicks);

    void runDelayed(@NotNull SchedulerType schedulerType, SchedulerCallBack callBack, long delayTicks);

    void runDelayed(@NotNull SchedulerType schedulerType, World world, int chunkX, int chunkZ, SchedulerCallBack callBack, long delayTicks);

    void runDelayed(@NotNull SchedulerType schedulerType, Location location, SchedulerCallBack callBack, long delayTicks);

    void runDelayed(@NotNull SchedulerType schedulerType, Entity entity, SchedulerCallBack callBack, @Nullable Runnable retired, long delayTicks);

    void runTask(@NotNull SchedulerType schedulerType, SchedulerCallBack callBack);

    void runTask(@NotNull SchedulerType schedulerType, World world, int chunkX, int chunkZ, SchedulerCallBack callBack);

    void runTask(@NotNull SchedulerType schedulerType, Location location, SchedulerCallBack callBack);

    void runTask(@NotNull SchedulerType schedulerType, Entity entity, SchedulerCallBack callBack, @Nullable Runnable retired);

    int scheduleSyncDelayed(@NotNull SchedulerType schedulerType, SchedulerCallBack callBack);

    int scheduleSyncDelayed(@NotNull SchedulerType schedulerType, World world, int chunkX, int chunkZ, SchedulerCallBack callBack);

    int scheduleSyncDelayed(@NotNull SchedulerType schedulerType, Location location, SchedulerCallBack callBack);

    int scheduleSyncDelayed(@NotNull SchedulerType schedulerType, SchedulerCallBack callBack, long delay);

    int scheduleSyncDelayed(@NotNull SchedulerType schedulerType, World world, int chunkX, int chunkZ, SchedulerCallBack callBack, long delay);

    int scheduleSyncDelayed(@NotNull SchedulerType schedulerType, Location location, SchedulerCallBack callBack, long delay);

    int scheduleSyncDelayed(@NotNull SchedulerType schedulerType, Entity entity, SchedulerCallBack callBack, @Nullable Runnable retired, long delay);

    int scheduleSyncRepeating(@NotNull SchedulerType schedulerType, SchedulerCallBack callBack, long delay, long period);

    int scheduleSyncRepeating(@NotNull SchedulerType schedulerType, World world, int chunkX, int chunkZ, SchedulerCallBack callBack, long delay, long period);

    int scheduleSyncRepeating(@NotNull SchedulerType schedulerType, Location location, SchedulerCallBack callBack, long delay, long period);

    int scheduleSyncRepeating(@NotNull SchedulerType schedulerType, Entity entity, SchedulerCallBack callBack, @Nullable Runnable retired, long delay, long period);

    void cancelAllTask();

    void cancelTask(int taskId);
}
