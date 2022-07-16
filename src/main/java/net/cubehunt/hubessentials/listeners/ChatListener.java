package net.cubehunt.hubessentials.listeners;

import lombok.RequiredArgsConstructor;
import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.User;
import net.cubehunt.hubessentials.utils.Color;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

@RequiredArgsConstructor
public class ChatListener implements Listener {

    private final HubEssentials plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(final AsyncPlayerChatEvent event) {
        User user = plugin.getUser(event.getPlayer());
        user.setNickname();

        String format = plugin.getSettings().getChatFormat();
        format = format.replace("{DISPLAYNAME}", "%1$s");
        format = format.replace("{MESSAGE}", "%2$s");
        format = format.replace("{PREFIX}", user.getPrefix());
        format = format.replace("{SUFFIX}", user.getSuffix());
        format = Color.minimessageToLegacy(format);
        format = format.replace("%1$s", "%1$s§r");
        event.setFormat(format);

        if (user.hasPermission("hubessentials.chatcolor")) {
            final String message = event.getMessage();
            event.setMessage(Color.minimessageToLegacy(message));
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerJoin(final PlayerJoinEvent event) {
        User user = plugin.getUser(event.getPlayer());

        if (user.getPlayer().hasPlayedBefore()) {
             event.setJoinMessage(plugin.getSettings().getPlayerJoinMessage(user.getPlayer()));
        } else {
            event.setJoinMessage(plugin.getSettings().getPlayerWelcomeMessage(user.getPlayer()));
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    public void onPlayerQuit(final PlayerQuitEvent event) {
        User user = plugin.getUser(event.getPlayer());
        event.setQuitMessage(plugin.getSettings().getPlayerQuitMessage(user.getPlayer()));
    }
}
