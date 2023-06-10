package io.papermc.lib.scheduler.adapters;

import io.papermc.lib.scheduler.ScheduledTask;
import io.papermc.lib.scheduler.SchedulerAdapter;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitScheduler;
import org.bukkit.scheduler.BukkitTask;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class BukkitSchedulerAdapter implements SchedulerAdapter {
    private final Plugin plugin;
    @SuppressWarnings("deprecation")
    private final BukkitScheduler scheduler;

    public BukkitSchedulerAdapter(Plugin plugin) {
        this.plugin = plugin;
        this.scheduler = plugin.getServer().getScheduler();
    }

    @Override
    public ScheduledTask runAsync(@NonNull Consumer<ScheduledTask> task) {
        final BukkitScheduledTask bukkitTask = new BukkitScheduledTask(false);
        return bukkitTask.setTask(scheduler.runTaskAsynchronously(
                plugin,
                () -> task.accept(bukkitTask)
        ));
    }

    @Override
    public ScheduledTask runAsyncDelayed(@NonNull Consumer<ScheduledTask> task, long delay, @NonNull TimeUnit unit) {
        final BukkitScheduledTask bukkitTask = new BukkitScheduledTask(false);
        return bukkitTask.setTask(scheduler.runTaskLaterAsynchronously(
                plugin,
                () -> task.accept(bukkitTask),
                unit.toSeconds(delay) * 20
        ));
    }

    @Override
    public ScheduledTask runAsyncRate(@NonNull Consumer<ScheduledTask> task, long delay, long period, @NonNull TimeUnit unit) {
        final BukkitScheduledTask bukkitTask = new BukkitScheduledTask(true);
        return bukkitTask.setTask(scheduler.runTaskTimerAsynchronously(
                plugin,
                () -> task.accept(bukkitTask),
                unit.toSeconds(delay) * 20,
                unit.toSeconds(period) * 20
        ));
    }

    @Override
    public void cancelAsyncTasks() {
        scheduler.cancelTasks(plugin);
    }

    @Override
    public void executeAtGlobal(@NonNull Runnable task) {
        scheduler.runTask(plugin, task);
    }

    @Override
    public ScheduledTask runAtGlobal(@NonNull Consumer<ScheduledTask> task) {
        final BukkitScheduledTask bukkitTask = new BukkitScheduledTask(false);
        return bukkitTask.setTask(scheduler.runTask(
                plugin,
                () -> task.accept(bukkitTask)
        ));
    }

    @Override
    public ScheduledTask runAtGlobalDelayed(@NonNull Consumer<ScheduledTask> task, long delay) {
        final BukkitScheduledTask bukkitTask = new BukkitScheduledTask(false);
        return bukkitTask.setTask(scheduler.runTaskLater(
                plugin,
                () -> task.accept(bukkitTask),
                delay
        ));
    }

    @Override
    public ScheduledTask runAtGlobalRate(@NonNull Consumer<ScheduledTask> task, long delay, long period) {
        final BukkitScheduledTask bukkitTask = new BukkitScheduledTask(true);
        return bukkitTask.setTask(scheduler.runTaskTimer(
                plugin,
                () -> task.accept(bukkitTask),
                delay,
                period
        ));
    }

    @Override
    public void cancelGlobalTasks() {
        scheduler.cancelTasks(plugin);
    }

    @Override
    public void executeAtRegion(@NonNull Location location, @NonNull Runnable task) {
        executeAtGlobal(task);
    }

    @Override
    public ScheduledTask runAtRegion(@NonNull Location location, @NonNull Consumer<ScheduledTask> task) {
        return runAtGlobal(task);
    }

    @Override
    public ScheduledTask runAtRegionDelayed(@NonNull Location location, @NonNull Consumer<ScheduledTask> task, long delay) {
        return runAtGlobalDelayed(task, delay);
    }

    @Override
    public ScheduledTask runAtRegionRate(@NonNull Location location, @NonNull Consumer<ScheduledTask> task, long delay, long period) {
        return runAtGlobalRate(task, delay, period);
    }

    @Override
    public void executeAtEntity(@NonNull Entity entity, @NonNull Runnable task) {
        executeAtGlobal(task);
    }

    @Override
    public ScheduledTask runAtEntity(@NonNull Entity entity, @NonNull Consumer<ScheduledTask> task) {
        return runAtGlobal(task);
    }

    @Override
    public ScheduledTask runAtEntityDelayed(@NonNull Entity entity, @NonNull Consumer<ScheduledTask> task, long delay) {
        return runAtGlobalDelayed(task, delay);
    }

    @Override
    public ScheduledTask runAtEntityRate(@NonNull Entity entity, @NonNull Consumer<ScheduledTask> task, long delay, long period) {
        return runAtGlobalRate(task, delay, period);
    }

    private static class BukkitScheduledTask implements ScheduledTask {
        private BukkitTask task;
        private final boolean repeated;

        private BukkitScheduledTask(boolean repeated) {
            this.repeated = repeated;
        }

        @Override
        public @NonNull Plugin getOwner() {
            return task.getOwner();
        }

        @Override
        public boolean isRepeating() {
            return repeated;
        }

        @Override
        public boolean isCancelled() {
            return task.isCancelled();
        }

        @Override
        public void cancel() {
            task.cancel();
        }

        public BukkitScheduledTask setTask(final @NonNull BukkitTask task) {
            this.task = task;
            return this;
        }
    }
}
