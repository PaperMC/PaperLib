package io.papermc.lib.features.inventoryholdersnapshot;

import org.bukkit.inventory.Inventory;

public interface InventoryHolderSnapshot {
    InventoryHolderSnapshotResult getHolder(Inventory inventory, boolean useSnapshot);
}
