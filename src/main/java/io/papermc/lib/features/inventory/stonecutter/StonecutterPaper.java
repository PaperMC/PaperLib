package io.papermc.lib.features.inventory.stonecutter;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StonecutterPaper implements Stonecutter {

    public boolean openStonecutter(@NotNull Player player) {
        player.openStonecutter(null, true);
        return false;
    }
}
