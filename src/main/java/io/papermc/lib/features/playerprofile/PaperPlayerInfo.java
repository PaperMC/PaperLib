package io.papermc.lib.features.playerprofile;

import com.destroystokyo.paper.profile.PlayerProfile;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PaperPlayerInfo implements PlayerInfo {
    private UUID uuid;
    private String name;

    public PaperPlayerInfo(UUID uuid) throws IOException {
        this.uuid = uuid;

        Optional<String> name = Optional.ofNullable(nameExpensive(uuid));
        this.name = name.orElse(null);
    }

    public PaperPlayerInfo(String name) throws IOException {
        this.name = name;

        Optional<UUID> uuid = Optional.ofNullable(uuidExpensive(name));
        this.uuid = uuid.orElse(null);
    }

    public UUID getUUID() { return uuid; }

    public String getName() { return name; }

    private static String nameExpensive(UUID uuid) throws IOException {
        // Currently-online lookup
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            return player.getName();
        }

        // Cached profile lookup
        PlayerProfile profile = Bukkit.createProfile(uuid);
        if ((profile.isComplete() || profile.completeFromCache()) && profile.getName() != null && profile.getId() != null) {
            return profile.getName();
        }

        // Network lookup
        if (profile.complete(false) && profile.getName() != null && profile.getId() != null) {
            return profile.getName();
        }

        // Sorry, nada
        throw new IOException("Could not load player data from Mojang (rate-limited?)");
    }

    private static UUID uuidExpensive(String name) throws IOException {
        // Currently-online lookup
        Player player = Bukkit.getPlayer(name);
        if (player != null) {
            return player.getUniqueId();
        }

        // Cached profile lookup
        PlayerProfile profile = Bukkit.createProfile(name);
        if ((profile.isComplete() || profile.completeFromCache()) && profile.getName() != null && profile.getId() != null) {
            return profile.getId();
        }

        // Network lookup
        if (profile.complete(false) && profile.getName() != null && profile.getId() != null) {
            return profile.getId();
        }

        // Sorry, nada
        throw new IOException("Could not load player data from Mojang (rate-limited?)");
    }
}
