package io.papermc.lib.features.asyncteleport;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import java.util.concurrent.CompletableFuture;

public interface AsyncTeleport {
    CompletableFuture<Boolean> teleportAsync(Entity entity, Location location, TeleportCause cause);

    CompletableFuture<Boolean> teleportAsync(Entity entity, Entity entity2, TeleportCause cause);
}
