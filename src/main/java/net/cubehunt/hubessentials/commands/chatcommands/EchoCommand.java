package net.cubehunt.hubessentials.commands.chatcommands;

import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.commands.BaseCommand;
import net.cubehunt.hubessentials.commands.CommandSource;
import net.cubehunt.hubessentials.exceptions.InsufficientPermissionException;
import net.cubehunt.hubessentials.exceptions.NotEnoughArgumentsException;

import java.util.List;

public class EchoCommand extends BaseCommand {

    private final HubEssentials plugin;

    public EchoCommand(HubEssentials plugin) {
        super("echo", "Sends a message to yourself.", "hubessentials.echo");
        this.plugin = plugin;
        this.setUsage("/echo <message>");
    }

    @Override
    public void execute(CommandSource sender, String[] args) throws Exception {
        if (!sender.isPlayer()) throw new Exception("<red>Only in-game players can execute this command!");
        if (!testPermissionSilent(sender.getSender())) throw new InsufficientPermissionException("<red>You do not have the permission!");

        if (args.length < 1) throw new NotEnoughArgumentsException(this.getUsage());
        String message = String.join(" ", args);
        sender.sendMessage(message);
    }

    @Override
    public List<String> onTabComplete(CommandSource sender, String[] args) {
        return null;
    }
}
