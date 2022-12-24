package io.papermc.lib.features.inventoryholdersnapshot;

import org.bukkit.inventory.Inventory;

public class InventoryHolderSnapshotOptionalSnapshots implements InventoryHolderSnapshot {
    @Override
    public InventoryHolderSnapshotResult getHolder(Inventory inventory, boolean useSnapshot) {
        return new InventoryHolderSnapshotResult(useSnapshot, inventory.getHolder(useSnapshot));
    }
}
