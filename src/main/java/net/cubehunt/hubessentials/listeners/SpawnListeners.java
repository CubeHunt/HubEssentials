package net.cubehunt.hubessentials.listeners;

import lombok.RequiredArgsConstructor;
import net.cubehunt.hubessentials.HubEssentials;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

@RequiredArgsConstructor
public class SpawnListeners implements Listener {

    private final HubEssentials plugin;

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission("hubessentials.spawn-on-join.exempt")) {
            if (!plugin.spawnExists()) {
                plugin.setSpawn(player.getWorld().getSpawnLocation());
            }
            player.teleport(plugin.getSpawn());
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        Player player = event.getPlayer();

        if (!plugin.spawnExists()) {
            plugin.setSpawn(player.getWorld().getSpawnLocation());
        }

        event.setRespawnLocation(plugin.getSpawn());
    }
}
