package io.papermc.lib;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

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
    public static Environment getEnvironment() {
        return ENVIRONMENT;
    }

    /**
     * If you have need to inject a custom Environment, such as running on your own fork, or unit tests, do it here.
     * @param environment Custom Environment
     */
    public static void setCustomEnvironment(Environment environment) {
        ENVIRONMENT = environment;
    }

    /**
     * Teleports an Entity to the target location, loading the chunk asynchronously first if needed.
     * @param entity The Entity to teleport
     * @param location The Location to Teleport to
     * @return Future that completes with the result of the teleport
     */
    public static CompletableFuture<Boolean> teleportAsync(Entity entity, Location location) {
        return ENVIRONMENT.teleport(entity, location);
    }

    /**
     * Gets the chunk at the target location, loading it asynchronously if needed.
     * @param loc Location to get chunk for
     * @return Future that completes with the chunk
     */
    public static CompletableFuture<Chunk> getChunkAtAsync(Location loc) {
        return getChunkAtAsync(loc.getWorld(), loc.getBlockX() >> 4, loc.getBlockZ() >> 4, true);
    }

    /**
     * Gets the chunk at the target location, loading it asynchronously if needed.
     * @param loc Location to get chunk for
     * @param gen Should the chunk generate or not. Only respected on some MC versions, 1.13 for CB, 1.12 for Paper
     * @return Future that completes with the chunk, or null if the chunk did not exists and generation was not requested.
     */
    public static CompletableFuture<Chunk> getChunkAtAsync(Location loc, boolean gen) {
        return getChunkAtAsync(loc.getWorld(), loc.getBlockX() >> 4, loc.getBlockZ() >> 4, gen);
    }

    /**
     * Gets the chunk at the target location, loading it asynchronously if needed.
     * @param world World to load chunk for
     * @param x X coordinate of the chunk to load
     * @param z Z coordinate of the chunk to load
     * @return Future that completes with the chunk
     */
    public static CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z) {
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
    public static CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z, boolean gen) {
        return ENVIRONMENT.getChunkAtAsync(world, x, z, gen);
    }

    /**
     * Checks if the chunk has been generated or not. Only works on Paper 1.12+ or any 1.13.1+ version
     * @param loc Location to check if the chunk is generated
     * @return If the chunk is generated or not
     */
    public static boolean isChunkGenerated(Location loc) {
        return isChunkGenerated(loc.getWorld(), loc.getBlockX() >> 4, loc.getBlockZ() >> 4);
    }

    /**
     * Checks if the chunk has been generated or not. Only works on Paper 1.12+ or any 1.13.1+ version
     * @param world World to check for
     * @param x X coordinate of the chunk to check
     * @param z Z coordinate of the chunk to checl
     * @return If the chunk is generated or not
     */
    public static boolean isChunkGenerated(World world, int x, int z) {
        return ENVIRONMENT.isChunkGenerated(world, x, z);
    }

    /**
     * Get's a BlockState, optionally not using a snapshot
     * @param block The block to get a State of
     * @param useSnapshot Whether or not to use a snapshot when supported
     * @return The BlockState
     */
    public PaperFeatures.BlockStateSnapshotResult getBlockState(Block block, boolean useSnapshot) {
        return ENVIRONMENT.getBlockState(block, useSnapshot);
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
    public static void suggestPaper(Plugin plugin) {
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
