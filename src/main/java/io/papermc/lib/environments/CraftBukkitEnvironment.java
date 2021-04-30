package io.papermc.lib.environments;

public class CraftBukkitEnvironment extends Environment {

    @Override
    public String getName() {
        return "CraftBukkit";
    }

    @Override
    public boolean isCraftBukkit() {
        return true;
    }
}
