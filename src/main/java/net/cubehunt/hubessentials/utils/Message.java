package net.cubehunt.hubessentials.utils;

import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.commands.CommandSource;
import net.kyori.adventure.audience.Audience;
import org.bukkit.command.CommandSender;

public final class Message {

    private Message() {
    }

    public static void sendMessage(final CommandSender sender, final String message) {
        Audience audience = HubEssentials.adventure().sender(sender);
        audience.sendMessage(Color.colorize(message));
    }

    public static void sendMessage(final CommandSource sender, final String message) {
        sendMessage(sender.getSender(), message);
    }

}