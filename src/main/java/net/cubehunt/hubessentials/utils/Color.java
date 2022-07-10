package net.cubehunt.hubessentials.utils;

import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.bungeecord.BungeeComponentSerializer;
import net.md_5.bungee.api.chat.BaseComponent;

public final class Color {

    private Color() {
    }

    public static BaseComponent[] colorize(final String input) {
        return BungeeComponentSerializer.get().serialize(MiniMessage.miniMessage().deserialize(input));
    }

//    public static List<Component> colorize(final List<String> input) {
//        List<Component> components = new ArrayList<>();
//        for (final String s : input) {
//            components.add(colorize(s));
//        }
//        return components;
//    }

}
