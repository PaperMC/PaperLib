package io.papermc.lib.features.asyncteleport;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;

public class AsyncTeleportSync implements AsyncTeleport {

    @Override
    public CompletableFuture<Boolean> teleportAsync(Entity entity, Location location) {
        return CompletableFuture.completedFuture(entity.teleport(location));
    }
}
