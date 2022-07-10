package net.cubehunt.hubessentials.listeners;

import lombok.RequiredArgsConstructor;
import net.cubehunt.hubessentials.HubEssentials;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

@RequiredArgsConstructor // Crea il costruttore con le variabili final (required)
// Altrimenti @AllArgsConstructor invece crea il costruttore con tutti le variabili della classe
public class SpawnListeners implements Listener {

    // Declaring a new HubEssentials Class Instance
    private final HubEssentials plugin;

    // Event Handler Methods
    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();

        if (!plugin.spawnExists()) {
            plugin.setSpawn(player.getWorld().getSpawnLocation());
        }

        player.teleport(plugin.getSpawn());
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
