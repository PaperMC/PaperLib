package io.papermc.lib.features.blockstatesnapshot;

import javax.annotation.Nonnull;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

/**
 * {@link BlockState} Snapshots were added in 1.12, this will always be no snapshots.
 */
public class BlockStateSnapshotBeforeSnapshots implements BlockStateSnapshot {

    @Override
    @Nonnull
    public BlockStateSnapshotResult getBlockState(@Nonnull Block block, boolean useSnapshot) {
        return new BlockStateSnapshotResult(false, block.getState());
    }
}
