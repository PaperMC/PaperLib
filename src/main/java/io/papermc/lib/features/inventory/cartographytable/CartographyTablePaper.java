package io.papermc.lib.features.inventory.cartographytable;

import org.bukkit.entity.Player;

public class CartographyTablePaper implements CartographyTable {

    public boolean openCartographyTable(Player player) {
        player.openCartographyTable(null, true);
        return false;
    }
}
