package net.cubehunt.hubessentials.perms.impl;

import net.cubehunt.hubessentials.HubEssentials;

import java.util.Arrays;
import java.util.List;

public class ModernVaultHandler extends AbstractVaultHandler {

    private final List<String> supportedPlugins = Arrays.asList("PermissionsEx", "LuckPerms");

    @Override
    public boolean tryProvider(HubEssentials plugin) {
        return super.canLoad() && supportedPlugins.contains(getEnabledPermsPlugin());
    }
}
