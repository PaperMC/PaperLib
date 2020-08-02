package io.papermc.lib.features.blockstatesnapshot;

import org.bukkit.block.Block;

public class BlockStateSnapshotOptionalSnapshots implements BlockStateSnapshotHandler {
    @Override
    public BlockStateSnapshotResult getBlockState(Block block, boolean useSnapshot) {
        return new BlockStateSnapshotResult(useSnapshot, block.getState(useSnapshot));
    }
}
