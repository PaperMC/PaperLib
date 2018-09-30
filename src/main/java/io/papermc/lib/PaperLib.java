package io.papermc.lib;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;

import java.util.concurrent.CompletableFuture;
import java.util.logging.Logger;

@SuppressWarnings("WeakerAccess")
public class PaperLib {

    public static Environment ENVIRONMENT = initialize();

    public CompletableFuture<Boolean> teleportAsync(Entity entity, Location location) {
        return ENVIRONMENT.teleport(entity, location);
    }

    public CompletableFuture<Chunk> getChunkAtAsync(Location loc) {
        return getChunkAtAsync(loc.getWorld(), loc.getBlockX() >> 4, loc.getBlockZ() >> 4, true);
    }

    public CompletableFuture<Chunk> getChunkAtAsync(Location loc, boolean gen) {
        return getChunkAtAsync(loc.getWorld(), loc.getBlockX() >> 4, loc.getBlockZ() >> 4, gen);
    }

    public CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z) {
        return getChunkAtAsync(world, x, z, true);
    }

    public CompletableFuture<Chunk> getChunkAtAsync(World world, int x, int z, boolean gen) {
        return ENVIRONMENT.getChunkAtAsync(world, x, z, gen);
    }

    private static Environment initialize() {
        try {
            Class.forName("com.destroystokyo.paper.PaperConfig");
            try {
                World.class.getDeclaredMethod("getChunkAtAsync", Location.class);
                return new PaperModernEnvironment();
            } catch (NoSuchMethodException e) {
                return new PaperLegacyEnvironment();
            }
        } catch (ClassNotFoundException e) {
            try {
                Class.forName("org.spigotmc.SpigoConfig");
                return new SpigotEnvironment();
            } catch (ClassNotFoundException e1) {
                return new CraftBukkitEnvironment();
            }
        }
    }

    public static boolean isPaper() {
        return ENVIRONMENT.isPaper();
    }

    public static void suggestPaper(Plugin plugin) {
        if (isPaper()) {
            return;
        }
        final Logger logger = plugin.getLogger();
        logger.warning("==============================================");
        logger.warning(plugin.getDescription().getName() + " works better if you use PaperMC");
        logger.warning("as your server software. Paper offers significant performance");
        logger.warning("improvements over " + ENVIRONMENT.getName() + ", and this plugin");
        logger.warning("has been developed to run faster if it runs on Paper.");
    }
}
