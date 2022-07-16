package net.cubehunt.hubessentials.commands.chatcommands;

import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.User;
import net.cubehunt.hubessentials.commands.BaseCommand;
import net.cubehunt.hubessentials.commands.CommandSource;
import net.cubehunt.hubessentials.exceptions.InsufficientPermissionException;
import net.cubehunt.hubessentials.exceptions.NotEnoughArgumentsException;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.List;

public class DirectMessageCommand extends BaseCommand {

    private final HubEssentials plugin;

    public DirectMessageCommand(HubEssentials plugin) {
        super("directmessage", "Sends a dm to another player", "hubessentials.directmessage", "dm");
        this.plugin = plugin;
        this.setUsage("/directmessage <player> <message>");
    }


    @Override
    public void execute(CommandSource sender, String[] args) throws Exception {
        if (!testPermissionSilent(sender.getSender())) {
            throw new InsufficientPermissionException("<red>You do not have the permission for this command!");
        }

        if (args.length < 2) throw new NotEnoughArgumentsException(this.getUsage());
        Player player = Bukkit.getPlayer(args[0]);
        if (player == null) throw new Exception("<red>The player " + args[0] + " does not exitst!");

        User receiver = plugin.getUser(player);
        receiver.sendMessage(sender.getSender().getName() + ": " + args[1]);
    }

    @Override
    public List<String> onTabComplete(CommandSource sender, String[] args) {
        return null;
    }
}
