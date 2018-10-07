package io.papermc.lib;

import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.concurrent.CompletableFuture;

/**
 * Async Chunk Loading for Paper version 1.9 to 1.12
 */
class AsyncChunkLoad_9_12 implements PaperFeatures.AsyncChunkLoad {
    @Override
    public CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z, boolean gen) {
        CompletableFuture<Chunk> future = new CompletableFuture<>();
        if (!gen && PaperLib.getMinecraftVersion() >= 12 && !world.isChunkGenerated(x, z)) {
            future.complete(null);
        } else {
            World.ChunkLoadCallback chunkLoadCallback = future::complete;
            world.getChunkAtAsync(x, z, chunkLoadCallback);
        }
        return future;
    }
}
