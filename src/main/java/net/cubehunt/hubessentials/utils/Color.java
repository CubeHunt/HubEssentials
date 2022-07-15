package net.cubehunt.hubessentials.utils;

import net.kyori.adventure.platform.bukkit.BukkitComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import net.md_5.bungee.api.ChatColor;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

    public static String minimessageToLegacy(final String input) {
        return BukkitComponentSerializer.legacy().serialize(colorize(input));
    }

    public static List<String> minimessageToLegacy(final List<String> input) {
        List<String> list = new ArrayList<>();
        for (final String s : input) {
            list.add(minimessageToLegacy(s));
        }
        return list;
    }

    public static String legacy(String input) {
        final Pattern pattern = Pattern.compile("&#[a-fA-F0-9]{6}");
        Matcher match = pattern.matcher(input);
        while (match.find()) {
            String color = input.substring(match.start(), match.end());
            input = input.replace(color, ChatColor.of(color) + "");
            match = pattern.matcher(input);
        }
        return ChatColor.translateAlternateColorCodes('&', input);
    }

}
