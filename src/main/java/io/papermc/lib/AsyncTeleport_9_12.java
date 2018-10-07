package io.papermc.lib;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;

class AsyncTeleport_9_12 implements PaperFeatures.AsyncTeleport {
    @Override
    public CompletableFuture<Boolean> teleportAsync(Entity entity, Location location) {
        int x = location.getBlockX() >> 4;
        int z = location.getBlockZ() >> 4;
        return PaperLib.getChunkAtAsync(entity.getWorld(), x, z, true).thenApply(chunk -> entity.teleport(location));
    }
}
