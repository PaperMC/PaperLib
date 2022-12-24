package io.papermc.lib.features.inventoryholdersnapshot;

import org.bukkit.inventory.InventoryHolder;
public class InventoryHolderSnapshotResult {
    private final boolean isSnapshot;
    private final InventoryHolder holder;

    public InventoryHolderSnapshotResult(boolean isSnapshot, InventoryHolder holder) {
        this.isSnapshot = isSnapshot;
        this.holder = holder;
    }

    public boolean isSnapshot() {
        return isSnapshot;
    }

    public InventoryHolder getHolder() {
        return holder;
    }
}
