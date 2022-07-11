package net.cubehunt.hubessentials.commands;

import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.exceptions.InsufficientPermissionException;
import net.cubehunt.hubessentials.exceptions.NotEnoughArgumentsException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static net.cubehunt.hubessentials.utils.Message.sendMessage;

public class HubEssentialsCommand extends BaseCommand {

    private final HubEssentials plugin;

    public HubEssentialsCommand(HubEssentials plugin) {
        super("hubessentials", "Shows the info of HubEssentials", "hubessentials.admin");
        this.plugin = plugin;
        this.setUsage("/hubessentials <info | version | reload | help>");
    }

    @Override
    public void execute(CommandSource sender, String[] args) throws Exception {
        if (!testPermissionSilent(sender.getSender())) {
            throw new InsufficientPermissionException("<red>You do not have the permission for this command!");
        }

        if (args.length < 1)
            throw new NotEnoughArgumentsException(this.getUsage());

        final CommandType cmd;
        try {
            cmd = CommandType.valueOf(args[0].toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new Exception(this.getUsage());
        }

        switch (cmd) {
            case INFO -> {
                sendMessage(sender,
                    "##### prefix #####\n" +
                    "<gray>Authors: <yellow>" + String.join(", ", plugin.getDescription().getAuthors()) + "\n" +
                    "<gray>Description: <yellow>" + plugin.getDescription().getDescription() + "\n" +
                    "<gray>Version: <yellow>" + plugin.getDescription().getVersion() + "\n" +
                    "<gray>Status: <green>TODO"
                );
            }
            case VERSION -> {
                sendMessage(sender, "<gray>HubEssentials Version: <green>" + plugin.getDescription().getVersion());
            }
            case RELOAD -> {
                plugin.reload();
                sendMessage(sender, "<green>HubEssentials has been reloaded successfully!");
            }
            case HELP -> {

            }
        }

    }

    @Override
    public List<String> onTabComplete(CommandSource sender, String[] args) {
        if (!testPermissionSilent(sender.getSender())) return Collections.emptyList();

        if (args.length == 1) {
            final List<String> options = new ArrayList<>();
            for (final CommandType ct : CommandType.values()) {
                options.add(ct.name().toLowerCase());
            }
            return options;
        }

        return Collections.emptyList();
    }

    private enum CommandType {
        INFO,
        VERSION,
        RELOAD,
        HELP
    }
}
