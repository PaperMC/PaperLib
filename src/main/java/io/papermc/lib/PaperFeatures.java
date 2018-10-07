package io.papermc.lib;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;

public class PaperFeatures {

    public interface ChunkIsGenerated {
        default boolean isChunkGenerated(World world, int x, int z) {
            return true;
        }
    }

    public interface AsyncChunkLoad {
        default CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z, boolean gen) {
            if (!gen && PaperLib.isChunkGenerated(world, x, z)) {
                return CompletableFuture.completedFuture(null);
            } else {
                return CompletableFuture.completedFuture(world.getChunkAt(x, z));
            }
        }
    }
    public interface AsyncTeleport {
        default CompletableFuture<Boolean> teleportAsync(Entity entity, Location location) {
            int x = location.getBlockX() >> 4;
            int z = location.getBlockZ() >> 4;
            return PaperLib.getChunkAtAsync(entity.getWorld(), x, z, true).thenApply(chunk -> entity.teleport(location));
        }
    }
}
