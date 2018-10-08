package io.papermc.lib.features.profilesupport;

import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ProfileSupportUnknown implements ProfileSupport {
    public CompletableFuture<UUID> getPlayerUUIDAsync(String playerName) throws IOException {
        return CompletableFuture.completedFuture(Bukkit.getOfflinePlayer(playerName).getUniqueId());
    }

    public CompletableFuture<String> getPlayerNameAsync(UUID playerUUID) throws IOException {
        return CompletableFuture.completedFuture(Bukkit.getOfflinePlayer(playerUUID).getName());
    }
}
