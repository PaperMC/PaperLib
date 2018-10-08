package io.papermc.lib.features.blockstatesnapshot;

import org.bukkit.block.BlockState;

public class BlockStateSnapshotResult {
    private final boolean isSnapshot;
    private final BlockState state;

    public BlockStateSnapshotResult(boolean isSnapshot, BlockState state) {
        this.isSnapshot = isSnapshot;
        this.state = state;
    }

    public boolean isSnapshot() {
        return isSnapshot;
    }

    public BlockState getState() {
        return state;
    }
}
