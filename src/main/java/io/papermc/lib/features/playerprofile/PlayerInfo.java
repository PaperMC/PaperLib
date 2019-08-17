package io.papermc.lib.features.playerprofile;

import java.util.UUID;
import javax.annotation.Nullable;

public interface PlayerInfo {
    /**
     * The name of the player.
     *
     * @return The name of the payer.
     */
    @Nullable String getName();

    /**
     * The UUID of the player.
     *
     * @return The UUID of the player.
     */
    @Nullable UUID getUUID();
}
