package io.papermc.lib.features.offlineplayers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class GetOfflinePlayerSpigot implements GetOfflinePlayer {
    @Override
    @Nullable
    public OfflinePlayer getOfflinePlayer(@Nonnull String name, boolean makeWebRequest) {
        return Bukkit.getOfflinePlayer(name);
    }
}
