package net.cubehunt.hubessentials.commands.spawncommands;

import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.commands.BaseCommand;
import net.cubehunt.hubessentials.commands.CommandSource;
import net.cubehunt.hubessentials.exceptions.InsufficientPermissionException;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

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
        if (!sender.isPlayer()) throw new Exception("Only players can execute this command!");

        Player player = sender.getPlayer();

        // verifica permesso
        if (!testPermissionSilent(player)) {
            throw new InsufficientPermissionException("You do not have the permission for this command!");
        }

        // logica per settare lo spawn
        final Location location = player.getLocation(); // Prendo la location del player
        plugin.setSpawn(location); // Setto lo spawn nel config

        // Da qui il sender è di sicuro un player
        player.sendMessage("Spawn set successfully!");

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
