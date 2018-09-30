package io.papermc.lib;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;

public class PaperLegacyEnvironment extends SpigotEnvironment {

    @Override
    public CompletableFuture<Boolean> teleport(Entity entity, Location location) {
        int x = location.getBlockX() >> 4;
        int z = location.getBlockZ() >> 4;
        return this.getChunkAtAsync(entity.getWorld(), x, z, true).thenApply(chunk -> entity.teleport(location));
    }

    @Override
    public String getName() {
        return "Paper (1.9-1.12)";
    }

    @Override
    public boolean isPaper() {
        return true;
    }

    @Override
    public CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z, boolean gen) {
        CompletableFuture<Chunk> future = new CompletableFuture<>();
        if (!gen && getMinecraftVersion() >= 12 && !world.isChunkGenerated(x, z)) {
            future.complete(null);
        } else {
            World.ChunkLoadCallback chunkLoadCallback = future::complete;
            world.getChunkAtAsync(x, z, chunkLoadCallback);
        }
        return future;
    }
}
