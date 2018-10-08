package io.papermc.lib.features.blockstatesnapshot;

import org.bukkit.block.Block;

/**
 * Block State Snapshots was added in 1.12, this will always be no snapshots
 */
public class BlockStateSnapshotBeforeSnapshots implements BlockStateSnapshot {

    @Override
    public BlockStateSnapshotResult getBlockState(Block block, boolean useSnapshot) {
        return new BlockStateSnapshotResult(false, block.getState());
    }
}
