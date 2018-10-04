package io.papermc.lib;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class Environment {

    private int minecraftVersion;

    Environment() {
        Pattern versionPattern = Pattern.compile("\\(MC: (\\d)\\.(\\d+)\\.?.*?\\)");
        Matcher matcher = versionPattern.matcher(Bukkit.getVersion());
        int version = 0;
        if (matcher.find()) {
            try {
                version = Integer.parseInt(matcher.toMatchResult().group(2), 0);
            } catch (Exception ignored) {
            }
        }
        this.minecraftVersion = version;
    }

    public abstract String getName();

    public CompletableFuture<Boolean> teleport(Entity entity, Location location) {
        return CompletableFuture.completedFuture(entity.teleport(location));
    }

    public int getMinecraftVersion() {
        return minecraftVersion;
    }

    public boolean isPaper() {
        return false;
    }

    public CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z, boolean gen) {
        if (!gen && getMinecraftVersion() >= 13 && !world.isChunkGenerated(x, z)) {
            return CompletableFuture.completedFuture(null);
        } else {
            return CompletableFuture.completedFuture(world.getChunkAt(x, z));
        }
    }

    public BlockState getBlockState(Block block, boolean useSnapshot) {
        return block.getState();
    }

    public boolean updateBlockState(BlockState blockState, boolean force, boolean applyPhysics) {
        return blockState.update(force, applyPhysics);
    }
}
