package io.papermc.lib.features.bedspawnlocation;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class BedSpawnLocationSync implements BedSpawnLocation {

    @Override
    public CompletableFuture<Location> getBedSpawnLocationAsync(Player player, boolean isUrgent) {
        return CompletableFuture.completedFuture(player.getBedSpawnLocation());
    }
}
