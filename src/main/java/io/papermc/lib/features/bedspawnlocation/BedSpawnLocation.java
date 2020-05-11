package io.papermc.lib.features.bedspawnlocation;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public interface BedSpawnLocation {
    CompletableFuture<Location> getBedSpawnLocationAsync(Player player, boolean isUrgent);
}
