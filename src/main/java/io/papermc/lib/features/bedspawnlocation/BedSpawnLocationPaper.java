package io.papermc.lib.features.bedspawnlocation;

import io.papermc.lib.PaperLib;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.concurrent.CompletableFuture;

public class BedSpawnLocationPaper implements BedSpawnLocation {

    @Override
    public CompletableFuture<Location> getBedSpawnLocationAsync(Player player, boolean isUrgent) {
        Location bedLocation = player.getPotentialBedLocation();
        if (bedLocation == null) {
            return CompletableFuture.completedFuture(null);
        }
        CompletableFuture<Location> future = new CompletableFuture<>();
        PaperLib.getChunkAtAsync(bedLocation.getWorld(), bedLocation.getBlockX(), bedLocation.getBlockZ(), false, isUrgent).thenAccept(chunk -> future.complete(player.getBedSpawnLocation())).exceptionally(ex -> {
            future.completeExceptionally(ex);
            return null;
        });
        return future;
    }
}
