package io.papermc.lib;

import org.bukkit.block.Block;

class BlockStateSnapshot_12 implements PaperFeatures.BlockStateSnapshot {
    @Override
    public PaperFeatures.BlockStateSnapshotResult getBlockState(Block block, boolean useSnapshot) {
        return new PaperFeatures.BlockStateSnapshotResult(useSnapshot, block.getState(useSnapshot));
    }
}
