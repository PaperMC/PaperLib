package io.papermc.lib.features.playerprofile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class BukkitPlayerInfo implements PlayerInfo {
    private UUID uuid;
    private String name;

    private static ConcurrentMap<UUID, String> uuidCache = new ConcurrentHashMap<>();
    private static ConcurrentMap<String, UUID> nameCache = new ConcurrentHashMap<>();

    private static final Object uuidCacheLock = new Object();
    private static final Object nameCacheLock = new Object();

    private static final JSONParser uuidParser = new JSONParser(); // Thread-safe access via synchronized lock
    private static final JSONParser nameParser = new JSONParser(); // Thread-safe access via synchronized lock

    public BukkitPlayerInfo(UUID uuid) throws IOException {
        this.uuid = uuid;

        Optional<String> name = Optional.ofNullable(uuidCache.getOrDefault(uuid, null));
        if (!name.isPresent()) {
            synchronized (uuidCacheLock) { // Synchronize for thread-safe JSONParser access + defeating potential race conditions causing multiple lookups
                name = Optional.ofNullable(uuidCache.getOrDefault(uuid, null));
                if (!name.isPresent()) {
                    name = Optional.ofNullable(nameExpensive(uuid));
                    name.ifPresent(v -> uuidCache.put(uuid, v));
                }
            }
        }

        this.name = name.orElse(null);
    }

    public BukkitPlayerInfo(String name) throws IOException {
        this.name = name;

        Optional<UUID> uuid = Optional.ofNullable(nameCache.getOrDefault(name, null));
        if (!uuid.isPresent()) {
            synchronized (nameCacheLock) { // Synchronize for thread-safe JSONParser access + defeating potential race conditions causing multiple lookups
                uuid = Optional.ofNullable(nameCache.getOrDefault(name, null));
                if (!uuid.isPresent()) {
                    uuid = Optional.ofNullable(uuidExpensive(name));
                    uuid.ifPresent(v -> nameCache.put(name, v));
                }
            }
        }

        this.uuid = uuid.orElse(null);
    }

    public UUID getUUID() { return uuid; }

    public String getName() { return name; }

    private static String nameExpensive(UUID uuid) throws IOException {
        // Currently-online lookup
        Player player = Bukkit.getPlayer(uuid);
        if (player != null) {
            nameCache.put(player.getName(), uuid);
            return player.getName();
        }

        // Network lookup
        HttpURLConnection conn = getConnection(new URL("https://api.mojang.com/user/profiles/" + uuid.toString().replace("-", "") + "/names"));

        int code = conn.getResponseCode();
        try (
                InputStream in = (code == 200) ? conn.getInputStream() : conn.getErrorStream(); // Ensure we always get some text
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader buffer = new BufferedReader(reader)
        ) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = buffer.readLine()) != null) {
                builder.append(line);
            }

            if (code == 200) {
                JSONArray json = (JSONArray) nameParser.parse(builder.toString());
                JSONObject last = (JSONObject) json.get(json.size() - 1);
                String name = (String) last.get("name");

                nameCache.put(name, uuid);
            } else if (code == 204) {
                // No data exists
                return null;
            }
        } catch (ParseException ex) {
            throw new IOException(ex.getMessage(), ex);
        }

        throw new IOException("Could not load player data from Mojang (rate-limited?)");
    }

    private static UUID uuidExpensive(String name) throws IOException {
        // Currently-online lookup
        Player player = Bukkit.getPlayer(name);
        if (player != null) {
            uuidCache.put(player.getUniqueId(), name);
            return player.getUniqueId();
        }

        // Network lookup
        HttpURLConnection conn = getConnection(new URL("https://api.mojang.com/users/profiles/minecraft/" + name));

        int code = conn.getResponseCode();
        try (
                InputStream in = (code == 200) ? conn.getInputStream() : conn.getErrorStream(); // Ensure we always get some text
                InputStreamReader reader = new InputStreamReader(in);
                BufferedReader buffer = new BufferedReader(reader)
        ) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = buffer.readLine()) != null) {
                builder.append(line);
            }

            if (code == 200) {
                JSONObject json = (JSONObject) uuidParser.parse(builder.toString());
                UUID uuid = UUID.fromString(((String) json.get("id")).replaceFirst("(\\p{XDigit}{8})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}{4})(\\p{XDigit}+)", "$1-$2-$3-$4-$5")); // Normalize UUID for fromString (expects dashes, Mojang returns non-dashed result)
                name = (String) json.get("name");

                uuidCache.put(uuid, name);
            } else if (code == 204) {
                // No data exists
                return null;
            }
        } catch (ParseException ex) {
            throw new IOException(ex.getMessage(), ex);
        }

        throw new IOException("Could not load player data from Mojang (rate-limited?)");
    }

    private static HttpURLConnection getConnection(URL url) throws IOException {
        HttpURLConnection conn = getBaseConnection(url);
        conn.setInstanceFollowRedirects(true);

        int status;
        boolean redirect;

        do {
            status = conn.getResponseCode();
            redirect = status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM || status == HttpURLConnection.HTTP_SEE_OTHER; // Follow redirects

            if (redirect) {
                // Set cookies on redirect and follow redirect URL
                String newUrl = conn.getHeaderField("Location");
                String cookies = conn.getHeaderField("Set-Cookie");

                conn = getBaseConnection(new URL(newUrl));
                conn.setRequestProperty("Cookie", cookies);
                conn.addRequestProperty("Accept-Language", "en-US,en;q=0.8");
            }
        } while (redirect);

        return conn;
    }

    private static HttpURLConnection getBaseConnection(URL url) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();

        // Set standard headers
        conn.setRequestProperty("Accept", "application/json");
        conn.setRequestProperty("Connection", "close");
        conn.setRequestProperty("User-Agent", "PaperMC/PaperLib");
        conn.setRequestMethod("GET");

        return conn;
    }
}
