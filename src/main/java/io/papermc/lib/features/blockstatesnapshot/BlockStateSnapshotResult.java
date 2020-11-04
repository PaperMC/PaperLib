package io.papermc.lib.features.blockstatesnapshot;

import javax.annotation.Nonnull;

import org.bukkit.block.Block;
import org.bukkit.block.BlockState;

import io.papermc.lib.PaperLib;
import io.papermc.lib.environments.Environment;

/**
 * Objects of this type are the result of {@link PaperLib#getBlockState(Block, boolean)}.
 * You can use a {@link BlockStateSnapshotResult} to obtain the actual {@link BlockState} but
 * also to determine whether a snapshot was taken or not.
 */
public class BlockStateSnapshotResult {

    private final boolean isSnapshot;
    private final BlockState state;

    /**
     * This creates a new {@link BlockStateSnapshotResult} with the given arguments.
     * This constructor is normally invoked by a {@link BlockStateSnapshotHandler}.
     * 
     * @param isSnapshot Whether this {@link BlockState} is a snapshot or not
     * @param state The corresponding {@link BlockState}
     */
    public BlockStateSnapshotResult(boolean isSnapshot, @Nonnull BlockState state) {
        this.isSnapshot = isSnapshot;
        this.state = state;
    }

    /**
     * This method returns whether the corresponding {@link BlockState} is a snapshot or not.
     * A {@link BlockState} will be a snapshot if explicitly requested or if the {@link Environment}
     * does not allow non-snapshot {@link BlockState}s.
     * 
     * @return Whether this {@link BlockState} is a snapshot
     */
    public boolean isSnapshot() {
        return isSnapshot;
    }

    /**
     * This returns the {@link BlockState} that was taken.
     * To check if it is a snapshot, see {@link #isSnapshot()}.
     * 
     * @return The captured {@link BlockState}.
     */
    @Nonnull
    public BlockState getState() {
        return state;
    }
}
