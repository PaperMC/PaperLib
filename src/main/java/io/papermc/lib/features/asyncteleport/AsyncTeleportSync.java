package io.papermc.lib.features.asyncteleport;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import java.util.concurrent.CompletableFuture;

public class AsyncTeleportSync implements AsyncTeleport {

    @Override
    public CompletableFuture<Boolean> teleportAsync(Entity entity, Location location, TeleportCause cause) {
        return CompletableFuture.completedFuture(entity.teleport(location, cause));
    }

}
