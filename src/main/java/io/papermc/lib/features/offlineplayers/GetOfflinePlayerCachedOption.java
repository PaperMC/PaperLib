package io.papermc.lib.features.offlineplayers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class GetOfflinePlayerCachedOption implements GetOfflinePlayer {
    @Override
    @Nullable
    public OfflinePlayer getOfflinePlayer(@Nonnull String name, boolean makeWebRequest) {
        if (makeWebRequest) {
            return Bukkit.getOfflinePlayer(name);
        } else {
            return Bukkit.getOfflinePlayerIfCached(name);
        }
    }
}
