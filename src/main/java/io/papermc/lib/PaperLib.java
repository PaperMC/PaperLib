package io.papermc.lib;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerTeleportEvent.TeleportCause;
import org.bukkit.plugin.Plugin;

import io.papermc.lib.environments.CraftBukkitEnvironment;
import io.papermc.lib.environments.Environment;
import io.papermc.lib.environments.PaperEnvironment;
import io.papermc.lib.environments.SpigotEnvironment;
import io.papermc.lib.features.blockstatesnapshot.BlockStateSnapshotResult;

/**
 * Utility methods that assist plugin developers accessing Paper features.
 * Bridges backwards compatability with Spigot and CraftBukkit so your plugin
 * will still work on those platforms, and fall back to less performant methods.
 */
@SuppressWarnings("WeakerAccess")
public class PaperLib {
    private PaperLib() {
        // Hide public constructor
    }

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
        return ENVIRONMENT.teleport(entity, location, TeleportCause.PLUGIN);
    }

    /**
     * Teleports an Entity to the target location, loading the chunk asynchronously first if needed.
     * @param entity The Entity to teleport
     * @param location The Location to Teleport to
     * @param cause The cause for the teleportation
     * @return Future that completes with the result of the teleport
     */
    @Nonnull
    public static CompletableFuture<Boolean> teleportAsync(@Nonnull Entity entity, @Nonnull Location location, TeleportCause cause) {
        return ENVIRONMENT.teleport(entity, location, cause);
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
        return ENVIRONMENT.getChunkAtAsync(world, x, z, gen, false);
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
    public static CompletableFuture<Chunk> getChunkAtAsync(@Nonnull World world, int x, int z, boolean gen, boolean isUrgent) {
        return ENVIRONMENT.getChunkAtAsync(world, x, z, gen, isUrgent);
    }

    /**
     * Gets the chunk at the target location, loading it asynchronously if needed, with highest priority if supported
     * @param world World to load chunk for
     * @param x X coordinate of the chunk to load
     * @param z Z coordinate of the chunk to load
     * @param gen Should the chunk generate or not. Only respected on some MC versions, 1.13 for CB, 1.12 for Paper
     * @return Future that completes with the chunk, or null if the chunk did not exists and generation was not requested.
     */
    @Nonnull
    public static CompletableFuture<Chunk> getChunkAtAsyncUrgently(@Nonnull World world, int x, int z, boolean gen) {
        return ENVIRONMENT.getChunkAtAsync(world, x, z, gen, true);
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
     * Gets the location where the target player will spawn at their bed, asynchronously if needed
     * @param player   The player whose bed spawn location to get.
     * @param isUrgent Whether or not this should be performed with highest priority when supported
     * @return Future that completes with the location of the bed spawn location, or null if the player
     * has not slept in a bed or if the bed spawn is invalid.
     */
    public static CompletableFuture<Location> getBedSpawnLocationAsync(@Nonnull Player player, boolean isUrgent) {
        return ENVIRONMENT.getBedSpawnLocationAsync(player, isUrgent);
    }
    
    /**
     * Gets an OfflinePlayer by the given name. If the Player is not cached, an optional web request
     * can be made to look up the UUID of that player. Web requests will block the current Thread though.
     * @param name           The name of this OfflinePlayer.
     * @param makeWebRequest Whether or not a web request for UUID lookups should be made.
     * @return OfflinePlayer by the given name or null if the player is not cached and no web request was made.
     */
    @Nullable
    public static OfflinePlayer getOfflinePlayer(@Nonnull String name, boolean makeWebRequest) {
        return ENVIRONMENT.getOfflinePlayer(name, makeWebRequest);
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
     * Gets the current Minecraft Pre-Release version if applicable, otherwise -1. IE: "1.14.3 Pre-Release 4" returns 4
     * @return The Pre-Release Version if applicable, otherwise -1
     */
    public static int getMinecraftPreReleaseVersion() {
        return ENVIRONMENT.getMinecraftPreReleaseVersion();
    }

    /**
     * Check if the server has access to the Spigot API
     * @return True for Spigot <em>and</em> Paper environments
     */
    public static boolean isSpigot() {
        return ENVIRONMENT.isSpigot();
    }

    /**
     * Check if the server has access to the Paper API
     * @return True for Paper environments
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
     * This passes the default logLevel of Level.INFO
     *
     * @param plugin Your plugin object
     * @see #suggestPaper(Plugin, Level) 
     */
    public static void suggestPaper(@Nonnull Plugin plugin) {
        suggestPaper(plugin, Level.INFO);
    }

    /**
     * Can be called during plugin initialization to inform the server owner they should switch to Paper
     *
     * If you do not mind helping spread Paper, please call this in your plugin onEnable to help spread
     * awareness about Paper, and encourage them that your plugin is better when used with Paper!
     *
     * @param plugin Your plugin object
     * @param logLevel The logLevel you want to choose
     */
    public static void suggestPaper(@Nonnull Plugin plugin, @Nonnull Level logLevel) {
        if (isPaper()) {
            return;
        }
        final String benefitsProperty = "paperlib.shown-benefits";
        final String pluginName = plugin.getDescription().getName();
        final Logger logger = plugin.getLogger();
        logger.log(logLevel, "====================================================");
        logger.log(logLevel, " " + pluginName + " works better if you use Paper ");
        logger.log(logLevel, " as your server software. ");
        if (System.getProperty(benefitsProperty) == null) {
            System.setProperty(benefitsProperty, "1");
            logger.log(logLevel, "  ");
            logger.log(logLevel, " Paper offers significant performance improvements,");
            logger.log(logLevel, " bug fixes, security enhancements and optional");
            logger.log(logLevel, " features for server owners to enhance their server.");
            logger.log(logLevel, "  ");
            logger.log(logLevel, " Paper includes Timings v2, which is significantly");
            logger.log(logLevel, " better at diagnosing lag problems over v1.");
            logger.log(logLevel, "  ");
            logger.log(logLevel, " All of your plugins should still work, and the");
            logger.log(logLevel, " Paper community will gladly help you fix any issues.");
            logger.log(logLevel, "  ");
            logger.log(logLevel, " Join the Paper Community @ https://papermc.io");
        }
        logger.log(logLevel, "====================================================");
    }
}
