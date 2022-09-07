package io.papermc.lib.features.inventory.grindstone;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class GrindstonePaper implements Grindstone {

    public boolean openGrindstone(@NotNull Player player) {
        player.openGrindstone(null, true);
        return false;
    }
}
