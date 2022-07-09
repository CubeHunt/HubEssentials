package net.cubehunt.hubessentials.commands.spawn_commands;

import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.commands.BaseCommand;
import net.cubehunt.hubessentials.commands.CommandSource;

import java.util.Collections;
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
        /*
        * Per evitare di avere un codice con molte indentazioni
        * si usa fare:
        * if (!sender.isPlayer()) throw new Exception("Only players can run this command")
        *
        * Dopo l'IF sopra si fanno le altre cose
        * */
        if (sender.isPlayer()) {
            sender.sendMessage("Spawn set successfully!");
        } else {
            // anche se é la console usa sempre sender.sendMessage, sender va bene sia per player che per console
            plugin.getLogger().log(Level.INFO, "Only in-game players can execute this command!");
        }

        /*
        * Alcuni metodi utili:
        * una volta che sai che é un player puoi verificare se ha il permesso relativo al comando
        * testPermissionSilent(sender.getPlayer());
        * */

    }

    @Override
    public List<String> onTabComplete(CommandSource sender, String[] args) {
        return Collections.emptyList();
    }
}
