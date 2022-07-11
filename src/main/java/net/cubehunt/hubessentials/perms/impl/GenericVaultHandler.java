package net.cubehunt.hubessentials.perms.impl;

import net.cubehunt.hubessentials.HubEssentials;

public class GenericVaultHandler extends AbstractVaultHandler {

    @Override
    public boolean tryProvider(HubEssentials plugin) {
        return super.canLoad();
    }
}
