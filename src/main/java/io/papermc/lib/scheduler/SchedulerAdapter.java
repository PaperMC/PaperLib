package io.papermc.lib.scheduler;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

public interface SchedulerAdapter {
    /**
     * Schedules the specified task to be executed asynchronously.
     *
     * @param task The task to execute.
     * @return The {@link ScheduledTask} that represents the scheduled task.
     */
    ScheduledTask runAsync(@NonNull Consumer<ScheduledTask> task);

    /**
     * Schedules the specified task to be executed asynchronously after the time delay has passed.
     *
     * @param task  The task to execute.
     * @param delay The time delay to pass before the task should be executed.
     * @param unit  The time unit for the delay and period.
     * @return The {@link ScheduledTask} that represents the scheduled task.
     */
    ScheduledTask runAsyncDelayed(@NonNull Consumer<ScheduledTask> task, long delay, @NonNull TimeUnit unit);

    /**
     * Schedules the specified task to be executed asynchronously after the delay has passed,
     * and then periodically executed with the specified period.
     *
     * @param task   The task to execute.
     * @param delay  The time delay to pass before the task should be executed.
     * @param period The time between task executions after the first execution of the task.
     * @param unit   The time unit for the delay and period.
     * @return The {@link ScheduledTask} that represents the scheduled task.
     */
    ScheduledTask runAsyncRate(@NonNull Consumer<ScheduledTask> task, long delay, long period, @NonNull TimeUnit unit);

    /**
     * Attempts to cancel all tasks scheduled by the plugin in the async scheduler.
     */
    void cancelAsyncTasks();

    /**
     * Schedules a task to be executed on the global region.
     *
     * @param task The task to execute.
     */
    void executeAtGlobal(@NonNull Runnable task);

    /**
     * Schedules a task to be executed on the global region on the next tick.
     *
     * @param task The task to execute.
     * @return The {@link ScheduledTask} that represents the scheduled task.
     */
    ScheduledTask runAtGlobal(@NonNull Consumer<ScheduledTask> task);

    /**
     * Schedules a task to be executed on the global region after the specified delay in ticks.
     *
     * @param task  The task to execute.
     * @param delay The time delay to pass before the task should be executed, in ticks.
     * @return The {@link ScheduledTask} that represents the scheduled task.
     */
    ScheduledTask runAtGlobalDelayed(@NonNull Consumer<ScheduledTask> task, long delay);

    /**
     * Schedules a repeating task to be executed on the global region
     * after the delay with the specified period.
     *
     * @param task   The task to execute.
     * @param delay  The time delay to pass before the task should be executed, in ticks.
     * @param period The time between task executions after the first execution of the task, in ticks.
     * @return The {@link ScheduledTask} that represents the scheduled task.
     */
    ScheduledTask runAtGlobalRate(@NonNull Consumer<ScheduledTask> task, long delay, long period);

    /**
     * Attempts to cancel all tasks scheduled by the plugin in the global region scheduler.
     */
    void cancelGlobalTasks();

    /**
     * Schedules a task to be executed on the region which owns the location.
     *
     * @param location The location at which the region executing should own.
     * @param task     The task to execute.
     */
    void executeAtRegion(@NonNull Location location, @NonNull Runnable task);

    /**
     * Schedules a task to be executed on the region which owns the location on the next tick.
     *
     * @param location The location at which the region executing should own.
     * @param task     The task to execute.
     * @return The {@link ScheduledTask} that represents the scheduled task.
     */
    ScheduledTask runAtRegion(@NonNull Location location, @NonNull Consumer<ScheduledTask> task);

    /**
     * Schedules a task to be executed on the region which owns the location after the specified delay in ticks.
     *
     * @param location The location at which the region executing should own.
     * @param task     The task to execute.
     * @param delay    The time delay to pass before the task should be executed, in ticks.
     * @return The {@link ScheduledTask} that represents the scheduled task.
     */
    ScheduledTask runAtRegionDelayed(@NonNull Location location, @NonNull Consumer<ScheduledTask> task, long delay);

    /**
     * Schedules a repeating task to be executed on the region which owns the location
     * after the delay with the specified period.
     *
     * @param location The location at which the region executing should own.
     * @param task     The task to execute.
     * @param delay    The time delay to pass before the task should be executed, in ticks.
     * @param period   The time between task executions after the first execution of the task, in ticks.
     * @return The {@link ScheduledTask} that represents the scheduled task.
     */
    ScheduledTask runAtRegionRate(@NonNull Location location, @NonNull Consumer<ScheduledTask> task, long delay, long period);

    /**
     * Schedules a task. If the task failed to schedule because the scheduler is retired (entity removed),
     * then returns {@code false}. Otherwise, either the run callback will be invoked after the specified delay,
     * or the retired callback will be invoked if the scheduler is retired.
     * Note that the retired callback is invoked in critical code, so it should not attempt to remove the entity,
     * remove other entities, load chunks, load worlds, modify ticket levels, etc.
     *
     * <p>
     *     It is guaranteed that the task and retired callback are invoked on the region which owns the entity.
     * </p>
     * @param entity The entity relative to which the scheduler is obtained.
     * @param task   The task to execute.
     */
    void executeAtEntity(@NonNull Entity entity, @NonNull Runnable task);

    /**
     * Schedules a task to execute on the next tick. If the task failed to schedule because the scheduler is retired (entity removed),
     * then returns {@code false}. Otherwise, either the run callback will be invoked after the specified delay,
     * or the retired callback will be invoked if the scheduler is retired.
     * Note that the retired callback is invoked in critical code, so it should not attempt to remove the entity,
     * remove other entities, load chunks, load worlds, modify ticket levels, etc.
     *
     * <p>
     *     It is guaranteed that the task and retired callback are invoked on the region which owns the entity.
     * </p>
     * @param entity The entity relative to which the scheduler is obtained.
     * @param task   The task to execute.
     * @return The {@link ScheduledTask} that represents the scheduled task.
     */
    ScheduledTask runAtEntity(@NonNull Entity entity, @NonNull Consumer<ScheduledTask> task);

    /**
     * Schedules a task with the given delay. If the task failed to schedule because the scheduler is retired (entity removed),
     * then returns {@code false}. Otherwise, either the run callback will be invoked after the specified delay,
     * or the retired callback will be invoked if the scheduler is retired.
     * Note that the retired callback is invoked in critical code, so it should not attempt to remove the entity,
     * remove other entities, load chunks, load worlds, modify ticket levels, etc.
     *
     * <p>
     *     It is guaranteed that the task and retired callback are invoked on the region which owns the entity.
     * </p>
     * @param entity The entity relative to which the scheduler is obtained.
     * @param task   The task to execute.
     * @param delay  The time delay to pass before the task should be executed, in ticks.
     * @return The {@link ScheduledTask} that represents the scheduled task.
     */
    ScheduledTask runAtEntityDelayed(@NonNull Entity entity, @NonNull Consumer<ScheduledTask> task, long delay);

    /**
     * Schedules a repeating task with the given delay and period. If the task failed to schedule because the scheduler is retired (entity removed),
     * then returns {@code false}. Otherwise, either the run callback will be invoked after the specified delay,
     * or the retired callback will be invoked if the scheduler is retired.
     * Note that the retired callback is invoked in critical code, so it should not attempt to remove the entity,
     * remove other entities, load chunks, load worlds, modify ticket levels, etc.
     *
     * <p>
     *     It is guaranteed that the task and retired callback are invoked on the region which owns the entity.
     * </p>
     * @param entity The entity relative to which the scheduler is obtained.
     * @param task   The task to execute.
     * @param delay  The time delay to pass before the task should be executed, in ticks.
     * @param period The time between task executions after the first execution of the task, in ticks.
     * @return The {@link ScheduledTask} that represents the scheduled task.
     */
    ScheduledTask runAtEntityRate(@NonNull Entity entity, @NonNull Consumer<ScheduledTask> task, long delay, long period);
}