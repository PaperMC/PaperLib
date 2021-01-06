package io.papermc.lib.features.offlineplayers;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.OfflinePlayer;

public interface GetOfflinePlayer {
    @Nullable
    OfflinePlayer getOfflinePlayerIfCached(@Nonnull String name, boolean makeWebRequest);
}
