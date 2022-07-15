package net.cubehunt.hubessentials.listeners;

import lombok.RequiredArgsConstructor;
import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.User;
import net.cubehunt.hubessentials.commandblocker.CommandToBlock;
import net.cubehunt.hubessentials.utils.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
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

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        User user = plugin.getUser(event.getPlayer());
        user.setNickname();
        user.setLoginTime(System.currentTimeMillis());

        // Spawn Functionality
        if (!user.hasPermission("hubessentials.spawn-on-join.exempt")) {
            if (!plugin.spawnExists()) {
                plugin.setSpawn(user.getWorld().getSpawnLocation());
            }
            user.getPlayer().teleport(plugin.getSpawn());
        }

        // HidePlayer Functionality
        if (plugin.getHidePlayer().isApplyHideStatusOnJoin() && user.getHideStatus()) {
            user.hidePlayers();
            user.getPlayer().getInventory().setItem(plugin.getHidePlayer().getSlot(), plugin.getHidePlayer().hideItem());
        } else {
            user.getPlayer().getInventory().setItem(plugin.getHidePlayer().getSlot(), plugin.getHidePlayer().showItem());
        }

        for (final Player p : plugin.getServer().getOnlinePlayers()) {
            final User forUser = plugin.getUser(p);
            if (forUser.getHideStatus()) {
                forUser.getPlayer().hidePlayer(plugin, user.getPlayer());
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(final PlayerQuitEvent event) {
        User user = plugin.getUser(event.getPlayer());
        user.setLogoutTime(System.currentTimeMillis());
        plugin.unloadUser(user.getPlayer().getUniqueId());
    }

    @EventHandler
    public void onPlayerRespawn(final PlayerRespawnEvent event) {
        // Spawn Functionality
        User user = plugin.getUser(event.getPlayer());
        user.setNickname();

        if (!plugin.spawnExists()) {
            plugin.setSpawn(user.getWorld().getSpawnLocation());
        }
        event.setRespawnLocation(plugin.getSpawn());
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

    @EventHandler
    public void onPlayerInteract(final PlayerInteractEvent event) {
        // HidePlayer Functionality
        User user = plugin.getUser(event.getPlayer());
        if (event.getHand() != EquipmentSlot.HAND) return;
        if (!event.hasItem()) return;

        final ItemStack item = event.getItem();

        NamespacedKey key = new NamespacedKey(plugin, "HIDE_STATUS");
        PersistentDataContainer container = item.getItemMeta().getPersistentDataContainer();
        if (container.has(key, PersistentDataType.STRING)) {
            String sKey = container.get(key, PersistentDataType.STRING);

            if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_AIR) return;

            if (!user.hasPermission("hubessentials.hideplayer.bypass-cooldown")) {
                if (plugin.getHidePlayer().inCooldown(user.getPlayer().getUniqueId())) {
                    user.sendMessage("<red>You must wait <gold>" + plugin.getHidePlayer().getCooldownTime() + " <red>seconds!");
                    event.setCancelled(true);
                    return;
                }
            }

            if (sKey.equals("HIDE")) {
                user.sendMessage("<green>Player visibility enabled!");
                user.getPlayer().getInventory().getItemInMainHand().setType(plugin.getHidePlayer().showItem().getType());
                user.getPlayer().getInventory().getItemInMainHand().setItemMeta(plugin.getHidePlayer().showItem().getItemMeta());
                user.showPlayers();
                user.updateHideStatus();
            } else if (sKey.equals("SHOW")) {
                user.sendMessage("<red>Player visibility disabled!");
                user.getPlayer().getInventory().getItemInMainHand().setType(plugin.getHidePlayer().hideItem().getType());
                user.getPlayer().getInventory().getItemInMainHand().setItemMeta(plugin.getHidePlayer().hideItem().getItemMeta());
                user.hidePlayers();
                user.updateHideStatus();
            }

            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerChat(final AsyncPlayerChatEvent event) {
        User user = plugin.getUser(event.getPlayer());
        user.setNickname();

        String format = "{DISPLAYNAME} ➠ <#d4d8d9>{MESSAGE}";
        format = Color.minimessageToLegacy(format);
        format = format.replace("{DISPLAYNAME}", "%1$s§r");
        format = format.replace("{MESSAGE}", "%2$s");
        event.setFormat(format);

        if (user.hasPermission("hubessentials.chatcolor")) {
            final String message = event.getMessage();
            event.setMessage(Color.minimessageToLegacy(message));
        }
    }

}
