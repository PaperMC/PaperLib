package io.papermc.lib.features.inventory.smithingtable;

import org.bukkit.entity.Player;

public class SmithingTablePaper implements SmithingTable {

    public boolean openSmithingTable(Player player) {
        player.openSmithingTable(null, true);
        return false;
    }
}
