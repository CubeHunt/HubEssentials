package net.cubehunt.hubessentials;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;
import java.util.concurrent.ConcurrentSkipListMap;

public class UserMap {

    private final HubEssentials plugin;

    private final ConcurrentSkipListMap<UUID, User> users = new ConcurrentSkipListMap<>();

    public UserMap(HubEssentials plugin) {
        this.plugin = plugin;
    }

    public boolean exists(final UUID uuid) {
        return users.containsKey(uuid);
    }

    public void loadUser(final Player player) {
        if (!exists(player.getUniqueId())) {
            users.put(player.getUniqueId(), new User(player, plugin));
        }
    }

    public void unloadUser(final UUID uuid) {
        if (exists(uuid)) {
            users.remove(uuid);
        }
    }

    public User getUser(final UUID uuid) {
        return getUser(Bukkit.getPlayer(uuid));
    }

    public User getUser(final Player player) {
        if (!exists(player.getUniqueId())) loadUser(player);
        return users.get(player.getUniqueId());
    }

}
