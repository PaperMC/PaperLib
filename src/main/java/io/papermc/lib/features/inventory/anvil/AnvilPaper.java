package io.papermc.lib.features.inventory.anvil;

import org.bukkit.entity.Player;

public class AnvilPaper implements Anvil {

    public boolean openAnvil(Player player) {
        player.openAnvil(null, true);

        return false;
    }
}
