package net.cubehunt.hubessentials.perms.impl;

import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.permission.Permission;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;

public abstract class AbstractVaultHandler extends SuperpermsHandler {

    protected static Permission perms = null;
    protected static Chat chat = null;

    private boolean setupProviders() {
        try {
            Class.forName("net.milkbowl.vault.permission.Permission");
            Class.forName("net.milkbowl.vault.chat.Chat");
        } catch (final ClassNotFoundException e) {
            return false;
        }

        final RegisteredServiceProvider<Permission> permsProvider = Bukkit.getServer().getServicesManager().getRegistration(Permission.class);
        final RegisteredServiceProvider<Chat> chatProvider = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
        if (permsProvider == null || chatProvider == null) return false;

        perms = permsProvider.getProvider();
        chat = chatProvider.getProvider();
        return perms != null && chat != null;
    }

    @Override
    public String getGroup(final Player player) {
        return perms.getPrimaryGroup(player);
    }

    @Override
    public List<String> getGroups(final Player player) {
        return Arrays.asList(perms.getPlayerGroups(player));
    }

    @Override
    public boolean inGroup(final Player player, final String group) {
        return perms.playerInGroup(player, group);
    }

    @Override
    public String getPrefix(final Player player) {
        final String playerPrefix = chat.getPlayerPrefix(player);
        if (playerPrefix != null) {
            return playerPrefix;
        }

        final String playerGroup = perms.getPrimaryGroup(player);
        if (playerGroup != null) {
            return chat.getGroupPrefix(player.getWorld().getName(), playerGroup);
        }

        return null;
    }

    @Override
    public String getSuffix(final Player player) {
        final String playerSuffix = chat.getPlayerSuffix(player);
        if (playerSuffix != null) {
            return playerSuffix;
        }

        final String playerGroup = perms.getPrimaryGroup(player);
        if (playerGroup != null) {
            return chat.getGroupSuffix(player.getWorld().getName(), playerGroup);
        }

        return null;
    }

    @Override
    public String getBackendName() {
        final String reportedPlugin = perms.getName();
        final String classPlugin = JavaPlugin.getProvidingPlugin(perms.getClass()).getName();

        if (reportedPlugin.equals(classPlugin)) {
            return reportedPlugin;
        }
        return reportedPlugin + " (" + classPlugin + ")";
    }

    boolean canLoad() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) return false;
        try {
            return setupProviders();
        } catch (final Throwable t) {
            return false;
        }
    }
}
