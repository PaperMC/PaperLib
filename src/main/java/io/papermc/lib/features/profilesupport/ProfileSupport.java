package io.papermc.lib.features.profilesupport;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ProfileSupport {
    CompletableFuture<UUID> getPlayerUUIDAsync(String playerName) throws IOException;

    CompletableFuture<String> getPlayerNameAsync(UUID playerUUID) throws IOException;
}
