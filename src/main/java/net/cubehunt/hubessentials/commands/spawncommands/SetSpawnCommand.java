package net.cubehunt.hubessentials.commands.spawncommands;

import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.commands.BaseCommand;
import net.cubehunt.hubessentials.commands.CommandSource;
import net.cubehunt.hubessentials.exceptions.InsufficientPermissionException;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Collections;
import java.util.List;

import static net.cubehunt.hubessentials.utils.Message.sendMessage;

public class SetSpawnCommand extends BaseCommand {

    private final HubEssentials plugin;

    public SetSpawnCommand(HubEssentials plugin) {
        super("setspawn", "Sets the spawn for the hub.", "hubessentials.setspawn");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSource sender, String[] args) throws Exception {
        if (!sender.isPlayer()) throw new Exception("<red>Only players can execute this command!");

        Player player = sender.getPlayer();

        if (!testPermissionSilent(player)) {
            throw new InsufficientPermissionException("<red>You do not have the permission for this command!");
        }

        final Location location = player.getLocation();
        plugin.setSpawn(location);

        sendMessage(player, "<green>Spawn set successfully!");
    }

    @Override
    public List<String> onTabComplete(CommandSource sender, String[] args) {
        return Collections.emptyList();
    }
}
