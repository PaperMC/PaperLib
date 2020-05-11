package io.papermc.lib.features.asyncchunks;

import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.concurrent.CompletableFuture;

public class AsyncChunksPaper_15 implements AsyncChunks {
    @Override
    public CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z, boolean gen, boolean isUrgent) {
        return world.getChunkAtAsync(x, z, gen, isUrgent);
    }
}
