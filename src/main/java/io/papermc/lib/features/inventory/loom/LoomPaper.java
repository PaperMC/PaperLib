package io.papermc.lib.features.inventory.loom;

import org.bukkit.entity.Player;

public class LoomPaper implements Loom {

    public boolean openLoom(Player player) {
        player.openLoom(null, true);
        return false;
    }
}
