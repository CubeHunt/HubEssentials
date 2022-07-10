package net.cubehunt.hubessentials.commandblocker;

import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.config.IConfig;

public class CommandBlocker implements IConfig {

    private final HubEssentials plugin;

    public CommandBlocker(HubEssentials plugin) {
        this.plugin = plugin;
    }

    @Override
    public void reloadConfig() {

    }
}
