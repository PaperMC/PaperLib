package io.papermc.lib.features.blockstatesnapshot;

import javax.annotation.Nonnull;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import io.papermc.lib.environments.Environment;

/**
 * This {@link BlockStateSnapshotHandler} is used when the {@link Environment} forces every
 * {@link BlockState} to be a snaphot.
 *
 */
public class BlockStateSnapshotNoOption implements BlockStateSnapshotHandler {

    @Override
    @Nonnull
    public BlockStateSnapshotResult getBlockState(@Nonnull Block block, boolean useSnapshot) {
        return new BlockStateSnapshotResult(true, block.getState());
    }
}
