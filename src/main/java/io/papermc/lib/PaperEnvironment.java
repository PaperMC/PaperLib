package io.papermc.lib;

public class PaperEnvironment extends Environment {

    public PaperEnvironment() {
        super();

        if (isVersion(13, 1)) {
            asyncChunkLoadHandler = new AsyncChunkLoad_13();
            asyncTeleportHandler = new AsyncTeleport_13();
        } else if (isVersion(9) && !isVersion(13)) {
            asyncChunkLoadHandler = new AsyncChunkLoad_9_12();
            asyncTeleportHandler = new AsyncTeleport_9_12();
        }
        if (isVersion(12)) {
            // Paper added this API in 1.12 with same signature spigot did in 1.13
            isGeneratedHandler = new ChunkIsGenerated_13();
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
