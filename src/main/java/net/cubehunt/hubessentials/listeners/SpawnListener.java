package net.cubehunt.hubessentials.listeners;

import lombok.RequiredArgsConstructor;
import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

@RequiredArgsConstructor
public class SpawnListener implements Listener {

    private final HubEssentials plugin;

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        User user = plugin.getUser(event.getPlayer());

        if (!user.hasPermission("hubessentials.spawn-on-join.exempt")) {
            if (!plugin.spawnExists()) {
                plugin.setSpawn(user.getWorld().getSpawnLocation());
            }
            user.getPlayer().teleport(plugin.getSpawn());
        }
    }

    @EventHandler
    public void onPlayerRespawn(final PlayerRespawnEvent event) {
        User user = plugin.getUser(event.getPlayer());
        user.setNickname();

        if (!plugin.spawnExists()) {
            plugin.setSpawn(user.getWorld().getSpawnLocation());
        }
        event.setRespawnLocation(plugin.getSpawn());
    }
}
