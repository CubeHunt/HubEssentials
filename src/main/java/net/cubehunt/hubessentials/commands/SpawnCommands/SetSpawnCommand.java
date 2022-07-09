package net.cubehunt.hubessentials.commands.SpawnCommands;

import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.commands.BaseCommand;
import net.cubehunt.hubessentials.commands.CommandSource;

import java.util.List;
import java.util.logging.Level;

public class SetSpawnCommand extends BaseCommand {

    private final HubEssentials plugin;

    // Setting command info
    public SetSpawnCommand(HubEssentials plugin) {
        super("setspawn", "Sets the spawn for the hub.", "hubessentials.setspawn");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSource sender, String[] args) throws Exception {
        if (sender.isPlayer()) {
            sender.sendMessage("Spawn set successfully!");
        } else {
            plugin.getLogger().log(Level.INFO, "Only in-game players can execute this command!");
        }
    }

    @Override
    public List<String> onTabComplete(CommandSource sender, String[] args) {
        return null;
    }
}
