package net.cubehunt.hubessentials.utils;

import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.util.ArrayList;
import java.util.List;

public final class Color {

    private Color() {
    }

    public static Component colorize(final String input) {
        return MiniMessage.miniMessage().deserialize(input);
    }

    public static List<Component> colorize(final List<String> input) {
        List<Component> components = new ArrayList<>();
        for (final String s : input) {
            components.add(colorize(s));
        }
        return components;
    }

    public static String toLegacyColor(final String input) {
        return BukkitComponentSerializer.legacy().serialize(colorize(input));
    }

    public static List<String> toLegacyColor(final List<String> input) {
        List<String> list = new ArrayList<>();
        for (final String s : input) {
            list.add(toLegacyColor(s));
        }
        return list;
    }

}
