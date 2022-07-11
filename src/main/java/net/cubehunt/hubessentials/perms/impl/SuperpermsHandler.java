package net.cubehunt.hubessentials.perms.impl;

import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.perms.IPermissionsHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.Arrays;
import java.util.List;

public class SuperpermsHandler implements IPermissionsHandler {

    @Override
    public String getGroup(final Player player) {
        return null;
    }

    @Override
    public List<String> getGroups(final Player player) {
        return null;
    }

    @Override
    public boolean inGroup(final Player player, final String group) {
        return player.hasPermission("group." + group);
    }

    @Override
    public String getPrefix(final Player player) {
        return null;
    }

    @Override
    public String getSuffix(final Player player) {
        return null;
    }

    @Override
    public String getBackendName() {
        return getEnabledPermsPlugin();
    }

    @Override
    public boolean tryProvider(HubEssentials plugin) {
        return getEnabledPermsPlugin() != null;
    }

    public String getEnabledPermsPlugin() {
        String enabledPermsPlugin = null;
        final List<String> specialCasePlugins = Arrays.asList(
                "PermissionsEx",
                "GroupManager",
                "SimplyPerms",
                "Privileges",
                "bPermissions",
                "zPermissions",
                "PermissionsBukkit",
                "DroxPerms",
                "xPerms",
                "LuckPerms"
        );
        for (final Plugin plugin : Bukkit.getPluginManager().getPlugins()) {
            if (specialCasePlugins.contains(plugin.getName())) {
                enabledPermsPlugin = plugin.getName();
                break;
            }
        }
        return enabledPermsPlugin;
    }
}
