package io.papermc.lib.features.asyncchunks;

import io.papermc.lib.PaperLib;
import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.concurrent.CompletableFuture;

public class AsyncChunksSync implements AsyncChunks {

    @Override
    public CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z, boolean gen) {
        if (!gen && !PaperLib.isChunkGenerated(world, x, z)) {
            return CompletableFuture.completedFuture(null);
        } else {
            return CompletableFuture.completedFuture(world.getChunkAt(x, z));
        }
    }
}
