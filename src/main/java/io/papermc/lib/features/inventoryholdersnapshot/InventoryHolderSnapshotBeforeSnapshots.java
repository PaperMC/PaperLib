package io.papermc.lib.features.inventoryholdersnapshot;

import org.bukkit.inventory.Inventory;

/**
 * Block State Snapshots was added in 1.12, this will always be no snapshots
 */
public class InventoryHolderSnapshotBeforeSnapshots implements InventoryHolderSnapshot {

    @Override
    public InventoryHolderSnapshotResult getHolder(Inventory inventory, boolean useSnapshot) {
        return new InventoryHolderSnapshotResult(false, inventory.getHolder());
    }
}
