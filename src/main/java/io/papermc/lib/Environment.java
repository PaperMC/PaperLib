package io.papermc.lib;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;

import java.util.concurrent.CompletableFuture;
import java.util.regex.MatchResult;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("WeakerAccess")
public abstract class Environment {

    private int minecraftVersion;
    private int minecraftPatchVersion;

    protected PaperFeatures.AsyncChunkLoad asyncChunkLoadHandler = new PaperFeatures.AsyncChunkLoad() {};
    protected PaperFeatures.AsyncTeleport asyncTeleportHandler = new PaperFeatures.AsyncTeleport() {};
    protected PaperFeatures.ChunkIsGenerated isGeneratedHandler = new PaperFeatures.ChunkIsGenerated() {};
    protected PaperFeatures.BlockStateSnapshot blockStateSnapshotHandler;

    Environment() {
        Pattern versionPattern = Pattern.compile("\\(MC: (\\d)\\.(\\d+)\\.?(\\d+?)?\\)");
        Matcher matcher = versionPattern.matcher(Bukkit.getVersion());
        int version = 0;
        int patchVersion = 0;
        if (matcher.find()) {
            MatchResult matchResult = matcher.toMatchResult();
            try {
                version = Integer.parseInt(matchResult.group(2), 0);
            } catch (Exception ignored) {
            }
            if (matchResult.groupCount() >= 3) {
                try {
                    patchVersion = Integer.parseInt(matchResult.group(3), 0);
                } catch (Exception ignored) {
                }
            }
        }
        this.minecraftVersion = version;
        this.minecraftPatchVersion = patchVersion;

        // Common to all environments
        if (isVersion(13, 1)) {
            isGeneratedHandler = new ChunkIsGenerated_13();
        }
        if (!isVersion(12)) {
            blockStateSnapshotHandler = new BlockStateSnapshot_11();
        } else {
            blockStateSnapshotHandler = new PaperFeatures.BlockStateSnapshot() {};
        }
        // TODO: Reflection based?
    }

    public abstract String getName();

    public CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z, boolean gen) {
        return asyncChunkLoadHandler.getChunkAtAsync(world, x, z, gen);
    }

    public CompletableFuture<Boolean> teleport(Entity entity, Location location) {
        return asyncTeleportHandler.teleportAsync(entity, location);
    }

    public boolean isChunkGenerated(World world, int x, int z) {
        return isGeneratedHandler.isChunkGenerated(world, x, z);
    }

    public PaperFeatures.BlockStateSnapshotResult getBlockState(Block block, boolean useSnapshot) {
        return blockStateSnapshotHandler.getBlockState(block, useSnapshot);
    }

    public boolean isVersion(int minor) {
        return isVersion(minor, 0);
    }

    public boolean isVersion(int minor, int patch) {
        return minecraftVersion > minor || (minecraftVersion >= minor && minecraftPatchVersion >= patch);
    }

    public int getMinecraftVersion() {
        return minecraftVersion;
    }

    public int getMinecraftPatchVersion() {
        return minecraftVersion;
    }

    public boolean isPaper() {
        return false;
    }
}
