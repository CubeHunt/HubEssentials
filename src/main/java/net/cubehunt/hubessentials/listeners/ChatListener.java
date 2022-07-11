package net.cubehunt.hubessentials.listeners;

import lombok.RequiredArgsConstructor;
import net.cubehunt.hubessentials.HubEssentials;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import static net.cubehunt.hubessentials.utils.Color.toLegacyColor;

@RequiredArgsConstructor
public class ChatListener implements Listener {

    private final HubEssentials plugin;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerChat(final AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        if (player.hasPermission("hubessentials.chatcolor")) {
            final String message = event.getMessage();
            event.setMessage(toLegacyColor(message));
        }
    }

}
