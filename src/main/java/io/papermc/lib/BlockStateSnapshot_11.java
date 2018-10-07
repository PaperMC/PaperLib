package io.papermc.lib;

import org.bukkit.block.Block;

/**
 * Block State Snapshots was added in 1.12, this will always be no snapshots
 */
class BlockStateSnapshot_11 implements PaperFeatures.BlockStateSnapshot {

    @Override
    public PaperFeatures.BlockStateSnapshotResult getBlockState(Block block, boolean useSnapshot) {
        return new PaperFeatures.BlockStateSnapshotResult(false, block.getState());
    }
}
