package net.cubehunt.hubessentials.listeners;

import lombok.RequiredArgsConstructor;
import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.User;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

@RequiredArgsConstructor
public class HidePlayerListener implements Listener {

    private final HubEssentials plugin;

    @EventHandler
    public void onPlayerJoin(final PlayerJoinEvent event) {
        User user = plugin.getUser(event.getPlayer());

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
    public void onPlayerInteract(final PlayerInteractEvent event) {
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
                    user.sendMessage("<red>You must wait <gold>" + plugin.getHidePlayer().getRemainingSeconds(user.getPlayer().getUniqueId()) + " <red>seconds!");
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
}
