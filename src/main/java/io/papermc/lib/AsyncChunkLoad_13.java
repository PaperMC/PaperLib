package io.papermc.lib;

import org.bukkit.Chunk;
import org.bukkit.World;

import java.util.concurrent.CompletableFuture;

class AsyncChunkLoad_13 implements PaperFeatures.AsyncChunkLoad {
    @Override
    public CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z, boolean gen) {
        return world.getChunkAtAsync(x, z, gen);
    }
}
