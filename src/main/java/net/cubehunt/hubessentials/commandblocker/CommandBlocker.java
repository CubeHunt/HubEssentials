package net.cubehunt.hubessentials.commandblocker;

import lombok.Getter;
import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.config.BaseConfiguration;
import net.cubehunt.hubessentials.config.IConfig;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public class CommandBlocker implements IConfig {

    private final HubEssentials plugin;

    private final BaseConfiguration config;

    @Getter
    private final Set<CommandToBlock> blockedCommands = new HashSet<>();

    public CommandBlocker(HubEssentials plugin) {
        this.plugin = plugin;
        this.config = new BaseConfiguration(
                new File(plugin.getDataFolder(), "blocked-commands.yml"),
                "/blocked-commands.yml"
        );
        reloadConfig();
    }

    @Override
    public void reloadConfig() {
        config.load();
        registerBlockedCommands();
    }

    // Read blocked-commands.yml and populate blockdCommands
    private void registerBlockedCommands() {
        blockedCommands.clear();
        Set<String> keys = config.getKeys("commands");

        for (final String key : keys) {
            final String permission = config.getString("commands." + key + ".permission", "");
            final String message = config.getString("commands." + key + ".message", "");
            blockedCommands.add(new CommandToBlock(key, permission, message));
        }
    }
}
