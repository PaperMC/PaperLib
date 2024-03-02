package io.papermc.lib.features.multischeduler.legacy;

import io.papermc.lib.features.multischeduler.models.Scheduler;
import io.papermc.lib.features.multischeduler.models.SchedulerCallBack;
import io.papermc.lib.features.multischeduler.models.SchedulerTask;
import io.papermc.lib.features.multischeduler.models.SchedulerType;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;



public class LegacyScheduler implements Scheduler {

    private final Plugin plugin;

    public LegacyScheduler(Plugin pluginBukkit) {
        this.plugin = pluginBukkit;
    }

    @Override
    public void runAtFixedRate(@NotNull SchedulerType schedulerType, SchedulerCallBack callBack, long initialDelayTicks, long periodTicks) {
        if (schedulerType.equals(SchedulerType.ASYNC)) {
            Bukkit.getScheduler().runTaskTimerAsynchronously(this.plugin, task -> {
                SchedulerTask schedulerTask = new LegacySchedulerTask(task);
                callBack.run(schedulerTask);
            }, initialDelayTicks, periodTicks);
        } else {
            Bukkit.getScheduler().runTaskTimer(this.plugin, task -> {
                SchedulerTask schedulerTask = new LegacySchedulerTask(task);
                callBack.run(schedulerTask);
            }, initialDelayTicks, periodTicks);
        }
    }

    @Override
    public void runAtFixedRate(@NotNull SchedulerType schedulerType, @Nullable World world, int chunkX, int chunkZ, SchedulerCallBack callBack, long initialDelayTicks, long periodTicks) {
        this.runAtFixedRate(schedulerType, callBack, initialDelayTicks, periodTicks);
    }

    @Override
    public void runAtFixedRate(@NotNull SchedulerType schedulerType, @Nullable Location location, SchedulerCallBack callBack, long initialDelayTicks, long periodTicks) {
        this.runAtFixedRate(schedulerType, callBack, initialDelayTicks, periodTicks);
    }

    @Override
    public void runAtFixedRate(@NotNull SchedulerType schedulerType, @Nullable Entity entity, SchedulerCallBack callBack, @Nullable Runnable retired, long initialDelayTicks, long periodTicks) {
        this.runAtFixedRate(schedulerType, callBack, initialDelayTicks, periodTicks);
    }

    @Override
    public void runDelayed(@NotNull SchedulerType schedulerType, SchedulerCallBack callBack, long delayTicks) {
        if (schedulerType.equals(SchedulerType.ASYNC)) {
            Bukkit.getScheduler().runTaskLaterAsynchronously(this.plugin, task -> {
                SchedulerTask schedulerTask = new LegacySchedulerTask(task);
                callBack.run(schedulerTask);
            }, delayTicks);
        } else {
            Bukkit.getScheduler().runTaskLater(this.plugin, task -> {
                SchedulerTask schedulerTask = new LegacySchedulerTask(task);
                callBack.run(schedulerTask);
            }, delayTicks);
        }
    }

    @Override
    public void runDelayed(@NotNull SchedulerType schedulerType, @Nullable World world, int chunkX, int chunkZ, SchedulerCallBack callBack, long delayTicks) {
        this.runDelayed(schedulerType, callBack, delayTicks);
    }

    @Override
    public void runDelayed(@NotNull SchedulerType schedulerType, @Nullable Location location, SchedulerCallBack callBack, long delayTicks) {
        this.runDelayed(schedulerType, callBack, delayTicks);
    }

    @Override
    public void runDelayed(@NotNull SchedulerType schedulerType, @Nullable Entity entity, SchedulerCallBack callBack, @Nullable Runnable retired, long delayTicks) {
        this.runDelayed(schedulerType, callBack, delayTicks);
    }

    @Override
    public void runTask(@NotNull SchedulerType schedulerType, SchedulerCallBack callBack) {
        if (schedulerType.equals(SchedulerType.ASYNC)) {
            Bukkit.getScheduler().runTaskAsynchronously(this.plugin, () -> {
                callBack.run(null);
            });
        } else {
            Bukkit.getScheduler().runTask(this.plugin, () -> {
                callBack.run(null);
            });
        }
    }

    @Override
    public void runTask(@NotNull SchedulerType schedulerType, @Nullable World world, int chunkX, int chunkZ, SchedulerCallBack callBack) {
        this.runTask(schedulerType, callBack);
    }

    @Override
    public void runTask(@NotNull SchedulerType schedulerType, @Nullable Location location, SchedulerCallBack callBack) {
        this.runTask(schedulerType, callBack);
    }

    @Override
    public void runTask(@NotNull SchedulerType schedulerType, @Nullable Entity entity, SchedulerCallBack callBack, @Nullable Runnable retired) {
        this.runTask(schedulerType, callBack);
    }

    @Override
    public int scheduleSyncDelayed(@NotNull SchedulerType schedulerType, SchedulerCallBack callBack) {
        if (schedulerType.equals(SchedulerType.ASYNC)) {
            return Bukkit.getScheduler().scheduleAsyncDelayedTask(this.plugin, () -> {
                callBack.run(null);
            });
        } else {
            return Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
                callBack.run(null);
            });
        }
    }

    @Override
    public int scheduleSyncDelayed(@NotNull SchedulerType schedulerType, World world, int chunkX, int chunkZ, SchedulerCallBack callBack) {
        return this.scheduleSyncDelayed(schedulerType, callBack);
    }

    @Override
    public int scheduleSyncDelayed(@NotNull SchedulerType schedulerType, Location location, SchedulerCallBack callBack) {
        return this.scheduleSyncDelayed(schedulerType, callBack);
    }

    @Override
    public int scheduleSyncDelayed(@NotNull SchedulerType schedulerType, SchedulerCallBack callBack, long delay) {
        if (schedulerType.equals(SchedulerType.ASYNC)) {
            return Bukkit.getScheduler().scheduleAsyncDelayedTask(this.plugin, () -> {
                callBack.run(null);
            }, delay);
        } else {
            return Bukkit.getScheduler().scheduleSyncDelayedTask(this.plugin, () -> {
                callBack.run(null);
            }, delay);
        }
    }

    @Override
    public int scheduleSyncDelayed(@NotNull SchedulerType schedulerType, World world, int chunkX, int chunkZ, SchedulerCallBack callBack, long delay) {
        return this.scheduleSyncDelayed(schedulerType, callBack, delay);
    }

    @Override
    public int scheduleSyncDelayed(@NotNull SchedulerType schedulerType, Location location, SchedulerCallBack callBack, long delay) {
        return this.scheduleSyncDelayed(schedulerType, callBack, delay);
    }

    @Override
    public int scheduleSyncDelayed(@NotNull SchedulerType schedulerType, Entity entity, SchedulerCallBack callBack, @Nullable Runnable retired, long delay) {
        return this.scheduleSyncDelayed(schedulerType, callBack, delay);
    }

    @Override
    public int scheduleSyncRepeating(@NotNull SchedulerType schedulerType, SchedulerCallBack callBack, long delay, long period) {
        if (schedulerType.equals(SchedulerType.ASYNC)) {
            return Bukkit.getScheduler().scheduleAsyncRepeatingTask(this.plugin, () -> {
                callBack.run(null);
            }, delay, period);
        } else {
            return Bukkit.getScheduler().scheduleSyncRepeatingTask(this.plugin, () -> {
                callBack.run(null);
            }, delay, period);
        }
    }

    @Override
    public int scheduleSyncRepeating(@NotNull SchedulerType schedulerType, World world, int chunkX, int chunkZ, SchedulerCallBack callBack, long delay, long period) {
        return this.scheduleSyncRepeating(schedulerType, callBack, delay, period);
    }

    @Override
    public int scheduleSyncRepeating(@NotNull SchedulerType schedulerType, Location location, SchedulerCallBack callBack, long delay, long period) {
        return this.scheduleSyncRepeating(schedulerType, callBack, delay, period);
    }

    @Override
    public int scheduleSyncRepeating(@NotNull SchedulerType schedulerType, Entity entity, SchedulerCallBack callBack, @Nullable Runnable retired, long delay, long period) {
        return this.scheduleSyncRepeating(schedulerType, callBack, delay, period);
    }

    @Override
    public void cancelAllTask() {
        Bukkit.getScheduler().cancelTasks(this.plugin);
    }

    @Override
    public void cancelTask(int taskId) {
        Bukkit.getScheduler().cancelTask(taskId);
    }
}
