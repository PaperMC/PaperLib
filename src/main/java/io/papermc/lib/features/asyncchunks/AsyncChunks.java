package io.papermc.lib.features.asyncchunks;

import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.concurrent.CompletableFuture;

public interface AsyncChunks {
    default CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z, boolean gen) {
        return getChunkAtAsync(world, x, z, gen, false);
    }
    CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z, boolean gen, boolean isUrgent);
}
