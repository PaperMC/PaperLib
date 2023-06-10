package io.papermc.lib.scheduler.adapters;

import io.papermc.lib.scheduler.ScheduledTask;
import io.papermc.lib.scheduler.SchedulerAdapter;
import io.papermc.paper.threadedregions.scheduler.AsyncScheduler;
import io.papermc.paper.threadedregions.scheduler.GlobalRegionScheduler;
import io.papermc.paper.threadedregions.scheduler.RegionScheduler;
import org.bukkit.Location;
import org.bukkit.Server;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public class FoliaSchedulerAdapter implements SchedulerAdapter {
    private static final Runnable DO_NOTHING = () -> {
    };
    private final Plugin plugin;
    private final AsyncScheduler asyncScheduler;
    private final GlobalRegionScheduler globalScheduler;
    private final RegionScheduler regionScheduler;

    public FoliaSchedulerAdapter(Plugin plugin) {
        this.plugin = plugin;

        final Server server = plugin.getServer();
        this.asyncScheduler = server.getAsyncScheduler();
        this.globalScheduler = server.getGlobalRegionScheduler();
        this.regionScheduler = server.getRegionScheduler();
    }

    @Override
    public ScheduledTask runAsync(@NonNull Consumer<ScheduledTask> task) {
        return new FoliaScheduledTask(asyncScheduler.runNow(
                plugin,
                foliaTask -> task.accept(new FoliaScheduledTask(foliaTask))
        ));
    }

    @Override
    public ScheduledTask runAsyncDelayed(@NonNull Consumer<ScheduledTask> task, long delay, @NonNull TimeUnit unit) {
        return new FoliaScheduledTask(asyncScheduler.runDelayed(
                plugin,
                foliaTask -> task.accept(new FoliaScheduledTask(foliaTask)),
                delay,
                unit
        ));
    }

    @Override
    public ScheduledTask runAsyncRate(@NonNull Consumer<ScheduledTask> task, long delay, long period, @NonNull TimeUnit unit) {
        return new FoliaScheduledTask(asyncScheduler.runAtFixedRate(
                plugin,
                foliaTask -> task.accept(new FoliaScheduledTask(foliaTask)),
                delay,
                period,
                unit
        ));
    }

    @Override
    public void cancelAsyncTasks() {
        asyncScheduler.cancelTasks(plugin);
    }

    @Override
    public void executeAtGlobal(@NonNull Runnable task) {
        globalScheduler.execute(plugin, task);
    }

    @Override
    public ScheduledTask runAtGlobal(@NonNull Consumer<ScheduledTask> task) {
        return new FoliaScheduledTask(globalScheduler.run(
                plugin,
                foliaTask -> task.accept(new FoliaScheduledTask(foliaTask))
        ));
    }

    @Override
    public ScheduledTask runAtGlobalDelayed(@NonNull Consumer<ScheduledTask> task, long delay) {
        return new FoliaScheduledTask(globalScheduler.runDelayed(
                plugin,
                foliaTask -> task.accept(new FoliaScheduledTask(foliaTask)),
                delay
        ));
    }

    @Override
    public ScheduledTask runAtGlobalRate(@NonNull Consumer<ScheduledTask> task, long delay, long period) {
        return new FoliaScheduledTask(globalScheduler.runAtFixedRate(
                plugin,
                foliaTask -> task.accept(new FoliaScheduledTask(foliaTask)),
                delay,
                period
        ));
    }

    @Override
    public void cancelGlobalTasks() {
        globalScheduler.cancelTasks(plugin);
    }

    @Override
    public void executeAtRegion(@NonNull Location location, @NonNull Runnable task) {
        regionScheduler.execute(plugin, location, task);
    }

    @Override
    public ScheduledTask runAtRegion(@NonNull Location location, @NonNull Consumer<ScheduledTask> task) {
        return new FoliaScheduledTask(regionScheduler.run(
                plugin,
                location,
                foliaTask -> task.accept(new FoliaScheduledTask(foliaTask))
        ));
    }

    @Override
    public ScheduledTask runAtRegionDelayed(@NonNull Location location, @NonNull Consumer<ScheduledTask> task, long delay) {
        return new FoliaScheduledTask(regionScheduler.runDelayed(
                plugin,
                location,
                foliaTask -> task.accept(new FoliaScheduledTask(foliaTask)),
                delay
        ));
    }

    @Override
    public ScheduledTask runAtRegionRate(@NonNull Location location, @NonNull Consumer<ScheduledTask> task, long delay, long period) {
        return new FoliaScheduledTask(regionScheduler.runAtFixedRate(
                plugin,
                location,
                foliaTask -> task.accept(new FoliaScheduledTask(foliaTask)),
                delay,
                period
        ));
    }

    @Override
    public void executeAtEntity(@NonNull Entity entity, @NonNull Runnable task) {
        entity.getScheduler().execute(
                plugin,
                task,
                DO_NOTHING,
                0
        );
    }

    @Override
    public ScheduledTask runAtEntity(@NonNull Entity entity, @NonNull Consumer<ScheduledTask> task) {
        return new FoliaScheduledTask(entity.getScheduler().run(
                plugin,
                foliaTask -> task.accept(new FoliaScheduledTask(foliaTask)),
                DO_NOTHING
        ));
    }

    @Override
    public ScheduledTask runAtEntityDelayed(@NonNull Entity entity, @NonNull Consumer<ScheduledTask> task, long delay) {
        return new FoliaScheduledTask(entity.getScheduler().runDelayed(
                plugin,
                foliaTask -> task.accept(new FoliaScheduledTask(foliaTask)),
                DO_NOTHING,
                delay
        ));
    }

    @Override
    public ScheduledTask runAtEntityRate(@NonNull Entity entity, @NonNull Consumer<ScheduledTask> task, long delay, long period) {
        return new FoliaScheduledTask(entity.getScheduler().runAtFixedRate(
                plugin,
                foliaTask -> task.accept(new FoliaScheduledTask(foliaTask)),
                DO_NOTHING,
                delay,
                period
        ));
    }

    private static class FoliaScheduledTask implements ScheduledTask {

        private final io.papermc.paper.threadedregions.scheduler.ScheduledTask task;

        private FoliaScheduledTask(io.papermc.paper.threadedregions.scheduler.ScheduledTask task) {
            this.task = task;
        }

        @Override
        public @NonNull Plugin getOwner() {
            return task.getOwningPlugin();
        }

        @Override
        public boolean isRepeating() {
            return task.isRepeatingTask();
        }

        @Override
        public boolean isCancelled() {
            return task.isCancelled();
        }

        @Override
        public void cancel() {
            task.cancel();
        }
    }
}
