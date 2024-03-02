package io.papermc.lib.features.multischeduler.folia;

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

import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;


public class FoliaScheduler implements Scheduler {

    private final Plugin plugin;
    private final ConcurrentHashMap<Integer, SchedulerTask> mapSchedulerTask = new ConcurrentHashMap<>();

    public FoliaScheduler(Plugin pluginBukkit) {
        this.plugin = pluginBukkit;
    }

    @Override
    public void runAtFixedRate(@NotNull SchedulerType schedulerType, SchedulerCallBack callBack, long initialDelayTicks, long periodTicks) {
        if (initialDelayTicks <= 0) {
            initialDelayTicks = 1;
        }
        if (periodTicks <= 0) {
            periodTicks = 1;
        }
        if (schedulerType.equals(SchedulerType.ASYNC)) {
            Bukkit.getAsyncScheduler().runAtFixedRate(this.plugin, task -> {
                SchedulerTask schedulerTask = new FoliaSchedulerTask(task);
                mapSchedulerTask.put(schedulerTask.getTaskId(), schedulerTask);
                callBack.run(schedulerTask);
            }, initialDelayTicks * 50, periodTicks * 50, TimeUnit.MILLISECONDS);
        } else {
            Bukkit.getGlobalRegionScheduler().runAtFixedRate(this.plugin, task -> {
                SchedulerTask schedulerTask = new FoliaSchedulerTask(task);
                mapSchedulerTask.put(schedulerTask.getTaskId(), schedulerTask);
                callBack.run(schedulerTask);
            }, initialDelayTicks, periodTicks);
        }
    }

    @Override
    public void runAtFixedRate(@NotNull SchedulerType schedulerType, World world, int chunkX, int chunkZ, SchedulerCallBack callBack, long initialDelayTicks, long periodTicks) {
        if (schedulerType.equals(SchedulerType.ASYNC)) {
            this.runAtFixedRate(schedulerType, callBack, initialDelayTicks, periodTicks);
        } else {
            if (initialDelayTicks <= 0) {
                initialDelayTicks = 1;
            }
            if (periodTicks <= 0) {
                periodTicks = 1;
            }
            Bukkit.getRegionScheduler().runAtFixedRate(this.plugin, world, chunkX, chunkZ, task -> {
                SchedulerTask schedulerTask = new FoliaSchedulerTask(task);
                mapSchedulerTask.put(schedulerTask.getTaskId(), schedulerTask);
                callBack.run(schedulerTask);
            }, initialDelayTicks, periodTicks);
        }
    }

    @Override
    public void runAtFixedRate(@NotNull SchedulerType schedulerType, Location location, SchedulerCallBack callBack, long initialDelayTicks, long periodTicks) {
        if (schedulerType.equals(SchedulerType.ASYNC)) {
            this.runAtFixedRate(schedulerType, callBack, initialDelayTicks, periodTicks);
        } else {
            if (initialDelayTicks <= 0) {
                initialDelayTicks = 1;
            }
            if (periodTicks <= 0) {
                periodTicks = 1;
            }
            Bukkit.getRegionScheduler().runAtFixedRate(this.plugin, location, task -> {
                SchedulerTask schedulerTask = new FoliaSchedulerTask(task);
                mapSchedulerTask.put(schedulerTask.getTaskId(), schedulerTask);
                callBack.run(schedulerTask);
            }, initialDelayTicks, periodTicks);
        }
    }

    @Override
    public void runAtFixedRate(@NotNull SchedulerType schedulerType, Entity entity, SchedulerCallBack callBack, @Nullable Runnable retired, long initialDelayTicks, long periodTicks) {
        if (schedulerType.equals(SchedulerType.ASYNC)) {
            this.runAtFixedRate(schedulerType, callBack, initialDelayTicks, periodTicks);
        } else {
            if (initialDelayTicks <= 0) {
                initialDelayTicks = 1;
            }
            if (periodTicks <= 0) {
                periodTicks = 1;
            }
            entity.getScheduler().runAtFixedRate(this.plugin, task -> {
                SchedulerTask schedulerTask = new FoliaSchedulerTask(task);
                mapSchedulerTask.put(schedulerTask.getTaskId(), schedulerTask);
                callBack.run(schedulerTask);
            }, retired, initialDelayTicks, periodTicks);
        }
    }

    @Override
    public void runDelayed(@NotNull SchedulerType schedulerType, SchedulerCallBack callBack, long delayTicks) {
        if (delayTicks <= 0) {
            delayTicks = 1;
        }
        if (schedulerType.equals(SchedulerType.ASYNC)) {
            Bukkit.getAsyncScheduler().runDelayed(this.plugin, task -> {
                SchedulerTask schedulerTask = new FoliaSchedulerTask(task);
                mapSchedulerTask.put(schedulerTask.getTaskId(), schedulerTask);
                callBack.run(schedulerTask);
            }, delayTicks * 50, TimeUnit.MILLISECONDS);
        } else {
            Bukkit.getGlobalRegionScheduler().runDelayed(this.plugin, task -> {
                SchedulerTask schedulerTask = new FoliaSchedulerTask(task);
                mapSchedulerTask.put(schedulerTask.getTaskId(), schedulerTask);
                callBack.run(schedulerTask);
            }, delayTicks);
        }
    }

    @Override
    public void runDelayed(@NotNull SchedulerType schedulerType, World world, int chunkX, int chunkZ, SchedulerCallBack callBack, long delayTicks) {
        if (schedulerType.equals(SchedulerType.ASYNC)) {
            this.runDelayed(schedulerType, callBack, delayTicks);
        } else {
            if (delayTicks <= 0) {
                delayTicks = 1;
            }
            Bukkit.getRegionScheduler().runDelayed(this.plugin, world, chunkX, chunkZ, task -> {
                SchedulerTask schedulerTask = new FoliaSchedulerTask(task);
                mapSchedulerTask.put(schedulerTask.getTaskId(), schedulerTask);
                callBack.run(schedulerTask);
            }, delayTicks);
        }
    }

    @Override
    public void runDelayed(@NotNull SchedulerType schedulerType, Location location, SchedulerCallBack callBack, long delayTicks) {
        if (schedulerType.equals(SchedulerType.ASYNC)) {
            this.runDelayed(schedulerType, callBack, delayTicks);
        } else {
            if (delayTicks <= 0) {
                delayTicks = 1;
            }
            Bukkit.getRegionScheduler().runDelayed(this.plugin, location, task -> {
                SchedulerTask schedulerTask = new FoliaSchedulerTask(task);
                mapSchedulerTask.put(schedulerTask.getTaskId(), schedulerTask);
                callBack.run(schedulerTask);
            }, delayTicks);
        }
    }

    @Override
    public void runDelayed(@NotNull SchedulerType schedulerType, Entity entity, SchedulerCallBack callBack, @Nullable Runnable retired, long delayTicks) {
        if (schedulerType.equals(SchedulerType.ASYNC)) {
            this.runDelayed(schedulerType, callBack, delayTicks);
        } else {
            if (delayTicks <= 0) {
                delayTicks = 1;
            }
            entity.getScheduler().runDelayed(this.plugin, task -> {
                SchedulerTask schedulerTask = new FoliaSchedulerTask(task);
                mapSchedulerTask.put(schedulerTask.getTaskId(), schedulerTask);
                callBack.run(schedulerTask);
            }, retired, delayTicks);
        }
    }

    @Override
    public void runTask(@NotNull SchedulerType schedulerType, SchedulerCallBack callBack) {
        if (schedulerType.equals(SchedulerType.ASYNC)) {
            Bukkit.getAsyncScheduler().runNow(this.plugin, task -> {
                SchedulerTask schedulerTask = new FoliaSchedulerTask(task);
                mapSchedulerTask.put(schedulerTask.getTaskId(), schedulerTask);
                callBack.run(schedulerTask);
            });
        } else {
            Bukkit.getGlobalRegionScheduler().run(this.plugin, task -> {
                SchedulerTask schedulerTask = new FoliaSchedulerTask(task);
                mapSchedulerTask.put(schedulerTask.getTaskId(), schedulerTask);
                callBack.run(schedulerTask);
            });
        }
    }

    @Override
    public void runTask(@NotNull SchedulerType schedulerType, World world, int chunkX, int chunkZ, SchedulerCallBack callBack) {
        if (schedulerType.equals(SchedulerType.ASYNC)) {
            this.runTask(schedulerType, callBack);
        } else {
            Bukkit.getRegionScheduler().run(this.plugin, world, chunkX, chunkZ, task -> {
                SchedulerTask schedulerTask = new FoliaSchedulerTask(task);
                mapSchedulerTask.put(schedulerTask.getTaskId(), schedulerTask);
                callBack.run(schedulerTask);
            });
        }
    }

    @Override
    public void runTask(@NotNull SchedulerType schedulerType, Location location, SchedulerCallBack callBack) {
        if (schedulerType.equals(SchedulerType.ASYNC)) {
            this.runTask(schedulerType, callBack);
        } else {
            Bukkit.getRegionScheduler().run(this.plugin, location, task -> {
                SchedulerTask schedulerTask = new FoliaSchedulerTask(task);
                mapSchedulerTask.put(schedulerTask.getTaskId(), schedulerTask);
                callBack.run(schedulerTask);
            });
        }
    }

    @Override
    public void runTask(@NotNull SchedulerType schedulerType, Entity entity, SchedulerCallBack callBack, @Nullable Runnable retired) {
        if (schedulerType.equals(SchedulerType.ASYNC)) {
            this.runTask(schedulerType, callBack);
        } else {
            entity.getScheduler().run(this.plugin, task -> {
                SchedulerTask schedulerTask = new FoliaSchedulerTask(task);
                mapSchedulerTask.put(schedulerTask.getTaskId(), schedulerTask);
                callBack.run(schedulerTask);
            }, retired);
        }
    }

    @Override
    public int scheduleSyncDelayed(@NotNull SchedulerType schedulerType, SchedulerCallBack callBack) {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        try {
            if (schedulerType.equals(SchedulerType.ASYNC)) {
                this.runTask(schedulerType, schedulerTask -> {
                    completableFuture.complete(schedulerTask != null ? schedulerTask.getTaskId() : 0);
                    callBack.run(schedulerTask);
                });
            } else {
                Bukkit.getGlobalRegionScheduler().execute(this.plugin, () -> {
                    completableFuture.complete(0);
                    callBack.run(null);
                });
            }
        } catch (Exception ignored) {
            completableFuture.complete(-1);
        }
        return completableFuture.join();
    }

    @Override
    public int scheduleSyncDelayed(@NotNull SchedulerType schedulerType, World world, int chunkX, int chunkZ, SchedulerCallBack callBack) {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        try {
            if (schedulerType.equals(SchedulerType.ASYNC)) {
                this.runTask(schedulerType, world, chunkX, chunkZ, schedulerTask -> {
                    completableFuture.complete(schedulerTask != null ? schedulerTask.getTaskId() : 0);
                    callBack.run(schedulerTask);
                });
            } else {
                Bukkit.getRegionScheduler().execute(this.plugin, world, chunkX, chunkZ, () -> {
                    completableFuture.complete(0);
                    callBack.run(null);
                });
            }
        } catch (Exception ignored) {
            completableFuture.complete(-1);
        }
        return completableFuture.join();
    }

    @Override
    public int scheduleSyncDelayed(@NotNull SchedulerType schedulerType, Location location, SchedulerCallBack callBack) {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        try {
            if (schedulerType.equals(SchedulerType.ASYNC)) {
                this.runTask(schedulerType, location, schedulerTask -> {
                    completableFuture.complete(schedulerTask != null ? schedulerTask.getTaskId() : 0);
                    callBack.run(schedulerTask);
                });
            } else {
                Bukkit.getRegionScheduler().execute(this.plugin, location, () -> {
                    completableFuture.complete(0);
                    callBack.run(null);
                });
            }
        } catch (Exception ignored) {
            completableFuture.complete(-1);
        }
        return completableFuture.join();
    }

    @Override
    public int scheduleSyncDelayed(@NotNull SchedulerType schedulerType, SchedulerCallBack callBack, long delay) {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        try {
            this.runDelayed(schedulerType, schedulerTask -> {
                completableFuture.complete(schedulerTask != null ? schedulerTask.getTaskId() : 0);
                callBack.run(schedulerTask);
            }, delay);
        } catch (Exception ignored) {
            completableFuture.complete(-1);
        }
        return completableFuture.join();
    }

    @Override
    public int scheduleSyncDelayed(@NotNull SchedulerType schedulerType, World world, int chunkX, int chunkZ, SchedulerCallBack callBack, long delay) {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        try {
            this.runDelayed(schedulerType, world, chunkX, chunkZ, schedulerTask -> {
                completableFuture.complete(schedulerTask != null ? schedulerTask.getTaskId() : 0);
                callBack.run(schedulerTask);
            }, delay);
        } catch (Exception ignored) {
            completableFuture.complete(-1);
        }
        return completableFuture.join();
    }

    @Override
    public int scheduleSyncDelayed(@NotNull SchedulerType schedulerType, Location location, SchedulerCallBack callBack, long delay) {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        try {
            this.runDelayed(schedulerType, location, schedulerTask -> {
                completableFuture.complete(schedulerTask != null ? schedulerTask.getTaskId() : 0);
                callBack.run(schedulerTask);
            }, delay);
        } catch (Exception ignored) {
            completableFuture.complete(-1);
        }
        return completableFuture.join();
    }

    @Override
    public int scheduleSyncDelayed(@NotNull SchedulerType schedulerType, Entity entity, SchedulerCallBack callBack, @Nullable Runnable retired, long delay) {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        try {
            if (schedulerType.equals(SchedulerType.ASYNC)) {
                this.runDelayed(schedulerType, entity, schedulerTask -> {
                    completableFuture.complete(schedulerTask != null ? schedulerTask.getTaskId() : 0);
                    callBack.run(schedulerTask);
                }, retired, delay);
            } else {
                entity.getScheduler().execute(this.plugin, () -> {
                    completableFuture.complete(0);
                    callBack.run(null);
                }, retired, delay);
            }
        } catch (Exception ignored) {
            completableFuture.complete(-1);
        }
        return completableFuture.join();
    }

    // ARRIVER ICI
    @Override
    public int scheduleSyncRepeating(@NotNull SchedulerType schedulerType, SchedulerCallBack callBack, long delay, long period) {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        try {
            this.runAtFixedRate(schedulerType, schedulerTask -> {
                completableFuture.complete(schedulerTask != null ? schedulerTask.getTaskId() : 0);
                callBack.run(schedulerTask);
            }, delay, period);
        } catch (Exception ignored) {
            completableFuture.complete(-1);
        }
        return completableFuture.join();
    }

    @Override
    public int scheduleSyncRepeating(@NotNull SchedulerType schedulerType, World world, int chunkX, int chunkZ, SchedulerCallBack callBack, long delay, long period) {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        try {
            this.runAtFixedRate(schedulerType, world, chunkX, chunkZ, schedulerTask -> {
                completableFuture.complete(schedulerTask != null ? schedulerTask.getTaskId() : 0);
                callBack.run(schedulerTask);
            }, delay, period);
        } catch (Exception ignored) {
            completableFuture.complete(-1);
        }
        return completableFuture.join();
    }

    @Override
    public int scheduleSyncRepeating(@NotNull SchedulerType schedulerType, Location location, SchedulerCallBack callBack, long delay, long period) {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        try {
            this.runAtFixedRate(schedulerType, location, schedulerTask -> {
                completableFuture.complete(schedulerTask != null ? schedulerTask.getTaskId() : 0);
                callBack.run(schedulerTask);
            }, delay, period);
        } catch (Exception ignored) {
            completableFuture.complete(-1);
        }
        return completableFuture.join();
    }

    @Override
    public int scheduleSyncRepeating(@NotNull SchedulerType schedulerType, Entity entity, SchedulerCallBack callBack, @Nullable Runnable retired, long delay, long period) {
        CompletableFuture<Integer> completableFuture = new CompletableFuture<>();
        try {
            this.runAtFixedRate(schedulerType, entity, schedulerTask -> {
                completableFuture.complete(schedulerTask != null ? schedulerTask.getTaskId() : 0);
                callBack.run(schedulerTask);
            }, retired, delay, period);
        } catch (Exception ignored) {
            completableFuture.complete(-1);
        }
        return completableFuture.join();
    }

    @Override
    public void cancelAllTask() {
        for (Map.Entry<Integer, SchedulerTask> entry : mapSchedulerTask.entrySet()) {
            SchedulerTask schedulerTaskInter = entry.getValue();
            schedulerTaskInter.cancel();
        }
    }

    @Override
    public void cancelTask(int taskId) {
        SchedulerTask schedulerTask = this.mapSchedulerTask.get(taskId);
        if (schedulerTask == null || schedulerTask.isCancelled()) return;
        schedulerTask.cancel();
    }
}
