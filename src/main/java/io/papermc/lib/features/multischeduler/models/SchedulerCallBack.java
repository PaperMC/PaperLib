package io.papermc.lib.features.multischeduler.models;

import org.jetbrains.annotations.Nullable;

public interface SchedulerCallBack {
    void run(@Nullable SchedulerTask schedulerTask);
}
