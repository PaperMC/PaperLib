package io.papermc.lib;

import io.papermc.lib.environments.CraftBukkitEnvironment;
import io.papermc.lib.environments.Environment;
import io.papermc.lib.environments.PaperEnvironment;
import io.papermc.lib.environments.SpigotEnvironment;
import io.papermc.lib.features.blockstatesnapshot.BlockStateSnapshotResult;
import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

/**
 * Utility methods that assist plugin developers accessing Paper features.
 * Bridges backwards compatability with Spigot and CraftBukkit so your plugin
 * will still work on those platforms, and fall back to less performant methods.
 */
@SuppressWarnings("WeakerAccess")
public class PaperLib {

    private static Environment ENVIRONMENT = initialize();

    private static Environment initialize() {
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            return new PaperEnvironment();
        } catch (ClassNotFoundException e) {
            try {
                Class.forName("org.spigotmc.SpigotConfig");
                return new SpigotEnvironment();
            } catch (ClassNotFoundException e1) {
                return new CraftBukkitEnvironment();
            }
        }
    }

    /**
     * Gets a reference to the current environment. All of the static util methods in this class points to this
     * environment.
     *
     * @return The Environment
     */
    @Nonnull
    public static Environment getEnvironment() {
        return ENVIRONMENT;
    }

    /**
     * If you have need to inject a custom Environment, such as running on your own fork, or unit tests, do it here.
     * @param environment Custom Environment
     */
    public static void setCustomEnvironment(@Nonnull Environment environment) {
        ENVIRONMENT = environment;
    }

    /**
     * Teleports an Entity to the target location, loading the chunk asynchronously first if needed.
     * @param entity The Entity to teleport
     * @param location The Location to Teleport to
     * @return Future that completes with the result of the teleport
     */
    @Nonnull
    public static CompletableFuture<Boolean> teleportAsync(@Nonnull Entity entity, @Nonnull Location location) {
        return ENVIRONMENT.teleport(entity, location);
    }

    /**
     * Gets the chunk at the target location, loading it asynchronously if needed.
     * @param loc Location to get chunk for
     * @return Future that completes with the chunk
     */
    @Nonnull
    public static CompletableFuture<Chunk> getChunkAtAsync(@Nonnull Location loc) {
        return getChunkAtAsync(loc.getWorld(), loc.getBlockX() >> 4, loc.getBlockZ() >> 4, true);
    }

    /**
     * Gets the chunk at the target location, loading it asynchronously if needed.
     * @param loc Location to get chunk for
     * @param gen Should the chunk generate or not. Only respected on some MC versions, 1.13 for CB, 1.12 for Paper
     * @return Future that completes with the chunk, or null if the chunk did not exists and generation was not requested.
     */
    @Nonnull
    public static CompletableFuture<Chunk> getChunkAtAsync(@Nonnull Location loc, boolean gen) {
        return getChunkAtAsync(loc.getWorld(), loc.getBlockX() >> 4, loc.getBlockZ() >> 4, gen);
    }

    /**
     * Gets the chunk at the target location, loading it asynchronously if needed.
     * @param world World to load chunk for
     * @param x X coordinate of the chunk to load
     * @param z Z coordinate of the chunk to load
     * @return Future that completes with the chunk
     */
    @Nonnull
    public static CompletableFuture<Chunk> getChunkAtAsync(@Nonnull World world, int x, int z) {
        return getChunkAtAsync(world, x, z, true);
    }

    /**
     * Gets the chunk at the target location, loading it asynchronously if needed.
     * @param world World to load chunk for
     * @param x X coordinate of the chunk to load
     * @param z Z coordinate of the chunk to load
     * @param gen Should the chunk generate or not. Only respected on some MC versions, 1.13 for CB, 1.12 for Paper
     * @return Future that completes with the chunk, or null if the chunk did not exists and generation was not requested.
     */
    @Nonnull
    public static CompletableFuture<Chunk> getChunkAtAsync(@Nonnull World world, int x, int z, boolean gen) {
        return ENVIRONMENT.getChunkAtAsync(world, x, z, gen);
    }

    /**
     * Checks if the chunk has been generated or not. Only works on Paper 1.12+ or any 1.13.1+ version
     * @param loc Location to check if the chunk is generated
     * @return If the chunk is generated or not
     */
    public static boolean isChunkGenerated(@Nonnull Location loc) {
        return isChunkGenerated(loc.getWorld(), loc.getBlockX() >> 4, loc.getBlockZ() >> 4);
    }

    /**
     * Checks if the chunk has been generated or not. Only works on Paper 1.12+ or any 1.13.1+ version
     * @param world World to check for
     * @param x X coordinate of the chunk to check
     * @param z Z coordinate of the chunk to checl
     * @return If the chunk is generated or not
     */
    public static boolean isChunkGenerated(@Nonnull World world, int x, int z) {
        return ENVIRONMENT.isChunkGenerated(world, x, z);
    }

    /**
     * Get's a BlockState, optionally not using a snapshot
     * @param block The block to get a State of
     * @param useSnapshot Whether or not to use a snapshot when supported
     * @return The BlockState
     */
    @Nonnull
    public static BlockStateSnapshotResult getBlockState(@Nonnull Block block, boolean useSnapshot) {
        return ENVIRONMENT.getBlockState(block, useSnapshot);
    }

    /**
     * Returns the UUID of a player found by name, or null
     * if a player was not found by the name provided.
     * If there was a rate-limit or other network error,
     * an IOException will be thrown.
     *
     * @param playerName The name of the player to fetch
     * @return The UUID of the specified player, or null if not found
     * @throws IOException if there was a rate-limit or other network error
     */
    @Nonnull
    public static CompletableFuture<UUID> getPlayerUUIDAsync(@Nonnull String playerName) throws IOException {
        return ENVIRONMENT.getPlayerUUIDAsync(playerName);
    }

    /**
     * Returns the name of a player found by UUID, or null
     * if a player was not found by the UUID provided.
     * If there was a rate-limit or other network error,
     * an IOException will be thrown.
     *
     * @param playerUUID The UUID of the player to fetch
     * @return The name of the specified player, or null if not found
     * @throws IOException if there was a rate-limit or other network error
     */
    @Nonnull
    public static CompletableFuture<String> getPlayerNameAsync(@Nonnull UUID playerUUID) throws IOException {
        return ENVIRONMENT.getPlayerNameAsync(playerUUID);
    }

    /**
     * Detects if the current MC version is at least the following version.
     *
     * Assumes 0 patch version.
     *
     * @param minor Min Minor Version
     * @return Meets the version requested
     */
    public static boolean isVersion(int minor) {
        return ENVIRONMENT.isVersion(minor);
    }

    /**
     * Detects if the current MC version is at least the following version.
     * @param minor Min Minor Version
     * @param patch Min Patch Version
     * @return Meets the version requested
     */
    public static boolean isVersion(int minor, int patch) {
        return ENVIRONMENT.isVersion(minor, patch);
    }

    /**
     * Gets the current Minecraft Minor version. IE: 1.13.1 returns 13
     * @return The Minor Version
     */
    public static int getMinecraftVersion() {
        return ENVIRONMENT.getMinecraftVersion();
    }

    /**
     * Gets the current Minecraft Patch version. IE: 1.13.1 returns 1
     * @return The Patch Version
     */
    public static int getMinecraftPatchVersion() {
        return ENVIRONMENT.getMinecraftPatchVersion();
    }

    /**
     * @return If the server is running Paper or not
     */
    public static boolean isPaper() {
        return ENVIRONMENT.isPaper();
    }

    /**
     * Can be called during plugin initialization to inform the server owner they should switch to Paper
     *
     * If you do not mind helping spread Paper, please call this in your plugin onEnable to help spread
     * awareness about Paper, and encourage them that your plugin is better when used with Paper!
     *
     * @param plugin Your plugin object
     */
    public static void suggestPaper(@Nonnull Plugin plugin) {
        if (isPaper()) {
            return;
        }
        final Logger logger = plugin.getLogger();
        logger.warning("==============================================");
        logger.warning(plugin.getDescription().getName() + " works better if you use Paper");
        logger.warning("as your server software. Paper offers significant performance");
        logger.warning("improvements over " + ENVIRONMENT.getName() + ", and this plugin");
        logger.warning("has been developed to run faster if it runs on Paper.");
        logger.warning("  ");
        logger.warning(" Learn more about Paper: https://papermc.io");
        logger.warning("==============================================");
    }
}
