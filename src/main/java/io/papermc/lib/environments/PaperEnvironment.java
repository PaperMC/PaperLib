package io.papermc.lib.environments;

import io.papermc.lib.features.asyncchunks.AsyncChunksPaper_13;
import io.papermc.lib.features.asyncchunks.AsyncChunksPaper_9_12;
import io.papermc.lib.features.asyncteleport.AsyncTeleportPaper;
import io.papermc.lib.features.blockstatesnapshot.BlockStateSnapshotOptionalSnapshots;
import io.papermc.lib.features.chunkisgenerated.ChunkIsGeneratedApiExists;

public class PaperEnvironment extends SpigotEnvironment {

    public PaperEnvironment() {
        super();

        if (isVersion(13, 1)) {
            asyncChunksHandler = new AsyncChunksPaper_13();
            asyncTeleportHandler = new AsyncTeleportPaper();
        } else if (isVersion(9) && !isVersion(13)) {
            asyncChunksHandler = new AsyncChunksPaper_9_12();
            asyncTeleportHandler = new AsyncTeleportPaper();
        }
        if (isVersion(12)) {
            // Paper added this API in 1.12 with same signature spigot did in 1.13
            isGeneratedHandler = new ChunkIsGeneratedApiExists();
            blockStateSnapshotHandler = new BlockStateSnapshotOptionalSnapshots();
        }
    }

    @Override
    public String getName() {
        return "Paper";
    }

    @Override
    public boolean isPaper() {
        return true;
    }

}
