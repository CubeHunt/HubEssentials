package net.cubehunt.hubessentials.listeners;

import lombok.RequiredArgsConstructor;
import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.User;
import net.cubehunt.hubessentials.commandblocker.CommandToBlock;
import net.cubehunt.hubessentials.utils.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

import java.util.Optional;
import java.util.Set;
import java.util.logging.Logger;

@RequiredArgsConstructor
public class PlayerListener implements Listener {

    private static final Logger logger = Logger.getLogger("HubEssentials");

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
