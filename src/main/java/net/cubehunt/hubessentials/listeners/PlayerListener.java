package net.cubehunt.hubessentials.listeners;

import lombok.RequiredArgsConstructor;
import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.User;
import net.cubehunt.hubessentials.commandblocker.CommandToBlock;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.Optional;
import java.util.Set;

@RequiredArgsConstructor
public class PlayerListener implements Listener {

    private final HubEssentials plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        User user = plugin.getUser(event.getPlayer());
        user.setLoginTime(System.currentTimeMillis());
        user.setNickname();
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        User user = plugin.getUser(event.getPlayer());
        user.setLogoutTime(System.currentTimeMillis());
        plugin.unloadUser(user.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerCommandPreprocess(final PlayerCommandPreprocessEvent event) {
        // CommandBlocker Functionality
        User user = plugin.getUser(event.getPlayer());
        String command = event.getMessage();

        final Set<CommandToBlock> commandToBlocks = plugin.getBlockedCommands();
        Optional<CommandToBlock> optional = commandToBlocks.stream().filter(cmd -> command.startsWith(cmd.command())).findAny();

        if (optional.isPresent()) {
            final CommandToBlock commandToBlock = optional.get();
            if (commandToBlock.permission().length() >= 1) {
                if (!user.hasPermission(commandToBlock.permission())) {
                    user.sendMessage(commandToBlock.message());
                    event.setCancelled(true);
                }
            }
        }
    }

}
