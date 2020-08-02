package io.papermc.lib.features.blockstatesnapshot;

import javax.annotation.Nonnull;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import io.papermc.lib.environments.Environment;

/**
 * A {@link BlockStateSnapshotHandler} is responsible for getting a {@link BlockState}
 * from a {@link Block}. This {@link BlockState} can be a snapshot or not, which depends
 * on the Server software that is used or the arguments that are passed.
 * <br>
 * You can use {@link #getBlockState(Block, boolean)} to the actual {@link BlockStateSnapshotResult}
 * that holds the {@link BlockState} and a boolean determining whether it was a snapshot or not.
 *
 */
@FunctionalInterface
public interface BlockStateSnapshotHandler {
    
    /**
     * This takes the {@link BlockState} of a given {@link Block}, optionally as a snapshot.
     * If the {@link Environment} forces a {@link BlockState} to be a snapshot, that argument
     * will be ignored.
     * 
     * @param block The {@link Block} to get the {@link BlockState} from
     * @param useSnapshot Whether a snapshot should be taken, might default to true in certain {@link Environment Environments}
     * @return A {@link BlockStateSnapshotResult}
     */
    @Nonnull
    BlockStateSnapshotResult getBlockState(@Nonnull Block block, boolean useSnapshot);

}
