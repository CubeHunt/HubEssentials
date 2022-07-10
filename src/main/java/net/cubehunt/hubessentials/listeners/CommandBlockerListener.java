package net.cubehunt.hubessentials.listeners;

import lombok.RequiredArgsConstructor;
import net.cubehunt.hubessentials.HubEssentials;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

@RequiredArgsConstructor
public class CommandBlockerListener implements Listener {

    private final HubEssentials plugin;

    @EventHandler
    public void onCommandPreProcess(final PlayerCommandPreprocessEvent event) {

    }

}
