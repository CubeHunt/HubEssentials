package net.cubehunt.hubessentials.listeners;

import lombok.RequiredArgsConstructor;
import net.cubehunt.hubessentials.HubEssentials;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

@RequiredArgsConstructor
public class PluginListener implements Listener {

    private final HubEssentials plugin;

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPluginEnable(final PluginEnableEvent event) {
        plugin.getPermissionsHandler().checkPermissions();
    }

}
