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

import static net.cubehunt.hubessentials.utils.Message.sendMessage;

@RequiredArgsConstructor
public class CommandBlockerListener implements Listener {

    private final HubEssentials plugin;

    @EventHandler
    public void onCommandPreProcess(final PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();
        String command = event.getMessage();

        final Set<CommandToBlock> commandToBlocks = plugin.getBlockedCommands();
        Optional<CommandToBlock> optional = commandToBlocks.stream().filter(cmd -> command.startsWith(cmd.command())).findAny();

        if (optional.isPresent()) {
            final CommandToBlock commandToBlock = optional.get();
            if (commandToBlock.permission().length() >= 1) {
                if (!player.hasPermission(commandToBlock.permission())) {
                    sendMessage(player, commandToBlock.message());
                    event.setCancelled(true);
                }
            }
        }
    }

}
