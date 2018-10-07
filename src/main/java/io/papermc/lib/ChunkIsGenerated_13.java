package io.papermc.lib;

import org.bukkit.World;

class ChunkIsGenerated_13 implements PaperFeatures.ChunkIsGenerated {
    @Override
    public boolean isChunkGenerated(World world, int x, int z) {
        return world.isChunkGenerated(x, z);
    }
}
