package io.papermc.lib.features.asyncteleport;

import org.bukkit.Location;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;

public interface AsyncTeleport {
    CompletableFuture<Boolean> teleportAsync(Entity entity, Location location);
}
