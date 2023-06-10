package io.papermc.lib.scheduler;

import org.bukkit.plugin.Plugin;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface ScheduledTask {
    /**
     * Returns the plugin that scheduled this task.
     *
     * @return the plugin that scheduled this task.
     */
    @NonNull Plugin getOwner();

    /**
     * Returns whether this task executes on a fixed period, as opposed to executing only once.
     *
     * @return whether this task executes on a fixed period, as opposed to executing only once.
     */
    boolean isRepeating();

    /**
     * Attempts to cancel this job, returning true if successful.
     *
     * @return the result of the cancellation attempt.
     */
    boolean isCancelled();

    /**
     * Attempts to cancel this task.
     */
    void cancel();
}
