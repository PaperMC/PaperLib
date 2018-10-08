package io.papermc.lib.features.profilesupport;

import com.destroystokyo.paper.profile.PlayerProfile;
import org.bukkit.Bukkit;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class ProfileSupportPaper implements ProfileSupport {
    public CompletableFuture<UUID> getPlayerUUIDAsync(String playerName) throws IOException {
        PlayerProfile profile = Bukkit.createProfile(playerName);
        if (profile.isComplete() || profile.completeFromCache()) {
            return CompletableFuture.completedFuture(profile.getId());
        }

        return CompletableFuture.supplyAsync(() -> {
            profile.complete(false);
            return profile.getId();
        });
    }

    public CompletableFuture<String> getPlayerNameAsync(UUID playerUUID) throws IOException {
        PlayerProfile profile = Bukkit.createProfile(playerUUID);
        if (profile.isComplete() || profile.completeFromCache()) {
            return CompletableFuture.completedFuture(profile.getName());
        }

        return CompletableFuture.supplyAsync(() -> {
            profile.complete(false);
            return profile.getName();
        });
    }
}
