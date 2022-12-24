package io.papermc.lib.features.inventoryholdersnapshot;

import org.bukkit.inventory.Inventory;

public class InventoryHolderSnapshotNoOption implements InventoryHolderSnapshot {
    @Override
    public InventoryHolderSnapshotResult getHolder(Inventory inventory, boolean useSnapshot) {
        return new InventoryHolderSnapshotResult(true, inventory.getHolder());
    }
}
