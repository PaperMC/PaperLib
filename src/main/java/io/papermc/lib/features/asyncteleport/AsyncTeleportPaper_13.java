package io.papermc.lib.features.asyncteleport;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;

import java.util.concurrent.CompletableFuture;

public class AsyncTeleportPaper_13 implements AsyncTeleport {

    @Override
    public CompletableFuture<Boolean> teleportAsync(Entity entity, Location location, TeleportCause cause) {
        return entity.teleportAsync(location, cause);
    }

}
