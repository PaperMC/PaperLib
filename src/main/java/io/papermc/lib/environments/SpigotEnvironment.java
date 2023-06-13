package io.papermc.lib.environments;

import io.papermc.lib.scheduler.SchedulerAdapter;
import io.papermc.lib.scheduler.adapters.BukkitSchedulerAdapter;
import org.bukkit.plugin.Plugin;

public class SpigotEnvironment extends CraftBukkitEnvironment {

    public SpigotEnvironment() {
        super();
    }

    @Override
    public String getName() {
        return "Spigot";
    }

    @Override
    public boolean isSpigot() {
        return true;
    }

    @Override
    public SchedulerAdapter createSchedulerAdapter(Plugin plugin) {
        return new BukkitSchedulerAdapter(plugin);
    }
}
