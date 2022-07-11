package net.cubehunt.hubessentials.perms.impl;

import net.cubehunt.hubessentials.HubEssentials;
import net.luckperms.api.LuckPerms;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;

public class LuckPermsHandler extends ModernVaultHandler {

    private LuckPerms luckPerms;
    private HubEssentials plugin;

    @Override
    public boolean tryProvider(HubEssentials plugin) {
        final RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
        if (provider != null) {
            this.luckPerms = provider.getProvider();
            this.plugin = plugin;
        }
        return luckPerms != null && super.tryProvider(plugin);
    }
}
