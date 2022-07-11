package net.cubehunt.hubessentials.commands;

import lombok.Getter;
import lombok.Setter;
import net.cubehunt.hubessentials.utils.Message;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public final class CommandSource {

    @Getter
    @Setter
    private CommandSender sender;

    public CommandSource(final CommandSender sender) {
        this.sender = sender;
    }

    public Player getPlayer() {
        if (sender instanceof Player player) {
            return player;
        }
        return null;
    }

    public boolean isPlayer() {
        return sender instanceof Player;
    }

    public void sendMessage(final String message) {
        if (!message.isEmpty()) Message.sendMessage(sender, message);
    }

    public boolean hasPermission(final String permission) {
        return sender.hasPermission(permission);
    }
}
