package io.papermc.lib.features.offlineplayers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

public class GetOfflinePlayerPaper implements GetOfflinePlayer {
    @Override
    @Nullable
    public OfflinePlayer getOfflinePlayerIfCached(@Nonnull String name, boolean makeWebRequest) {
        if (makeWebRequest) {
            return Bukkit.getOfflinePlayer(name);
        } else {
            return Bukkit.getOfflinePlayerIfCached(name);
        }
    }
}