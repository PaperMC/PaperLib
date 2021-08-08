package io.papermc.lib.features;

public enum PaperFeature {
    ASYNC_CHUNK_LOADING("Asynchronous Chunk Loading"),
    ASYNC_TELEPORT("Asynchronous Teleporting"),
    BED_SPAWN_LOCATION("Bed Spawn Locations"),
    BLOCK_STATE_SNAPSHOT("Block Snapshots");

    PaperFeature(String feature) {
        this.feature = feature;
    }

    private final String feature;

    public String getFeature() {
        return feature;
    }
}
