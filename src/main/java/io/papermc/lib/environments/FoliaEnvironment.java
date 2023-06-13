package io.papermc.lib.environments;

import io.papermc.lib.scheduler.SchedulerAdapter;
import io.papermc.lib.scheduler.adapters.FoliaSchedulerAdapter;
import org.bukkit.plugin.Plugin;

public class FoliaEnvironment extends PaperEnvironment {
    public FoliaEnvironment() {
        super();
    }

    @Override
    public String getName() {
        return "Folia";
    }

    @Override
    public boolean isFolia() {
        return true;
    }

    @Override
    public SchedulerAdapter createSchedulerAdapter(Plugin plugin) {
        return new FoliaSchedulerAdapter(plugin);
    }
}
