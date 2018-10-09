package io.papermc.lib.features.profilesupport;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public interface ProfileSupport {
    CompletableFuture<UUID> getPlayerUUID(String playerName) throws IOException;

    CompletableFuture<String> getPlayerName(UUID playerUUID) throws IOException;
}
