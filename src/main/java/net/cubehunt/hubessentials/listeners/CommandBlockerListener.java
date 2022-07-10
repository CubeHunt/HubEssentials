package net.cubehunt.hubessentials.listeners;

import lombok.RequiredArgsConstructor;
import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.commandblocker.CommandToBlock;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.Optional;
import java.util.Set;

import static net.cubehunt.hubessentials.utils.Color.colorize;

@RequiredArgsConstructor
public class CommandBlockerListener implements Listener {

    private final HubEssentials plugin;

    @EventHandler
    public void onCommandPreProcess(final PlayerCommandPreprocessEvent event) {

    }

}
