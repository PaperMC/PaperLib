package io.papermc;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;

public class PaperModernEnvironment extends PaperLegacyEnvironment {

    @Override
    public CompletableFuture<Boolean> teleport(Entity entity, Location location) {
        return entity.teleportAsync(location);
    }

    @Override
    public CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z, boolean gen) {
        return world.getChunkAtAsync(x, z, gen);
    }
}
