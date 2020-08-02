package io.papermc.lib.features.blockstatesnapshot;

import org.bukkit.block.Block;

@FunctionalInterface
public interface BlockStateSnapshotHandler {

    BlockStateSnapshotResult getBlockState(Block block, boolean useSnapshot);

}
