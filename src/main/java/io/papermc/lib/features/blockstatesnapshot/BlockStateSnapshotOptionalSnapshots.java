package io.papermc.lib.features.blockstatesnapshot;

import javax.annotation.Nonnull;

import org.bukkit.block.Block;

import io.papermc.lib.environments.Environment;

/**
 * This {@link BlockStateSnapshotHandler} allows the developer to decide whether or not to
 * take a snapshot.
 * if this handler is used, then the {@link Environment} supports both snapshots and non-snapshots.
 */
public class BlockStateSnapshotOptionalSnapshots implements BlockStateSnapshotHandler {

    @Override
    @Nonnull
    public BlockStateSnapshotResult getBlockState(@Nonnull Block block, boolean useSnapshot) {
        return new BlockStateSnapshotResult(useSnapshot, block.getState(useSnapshot));
    }
}
