package net.cubehunt.hubessentials.commands.chatcommands;

import net.cubehunt.hubessentials.HubEssentials;
import net.cubehunt.hubessentials.Settings;
import net.cubehunt.hubessentials.User;
import net.cubehunt.hubessentials.commands.BaseCommand;
import net.cubehunt.hubessentials.commands.CommandSource;
import net.cubehunt.hubessentials.exceptions.InsufficientPermissionException;
import net.cubehunt.hubessentials.exceptions.NotEnoughArgumentsException;

import javax.swing.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NickcolorsCommand extends BaseCommand {

    private final HubEssentials plugin;

    public NickcolorsCommand(HubEssentials plugin) {
        super("nickcolors", "Lists the available nickname colors", "hubessentials.chat.nickcolor", "nickcolor");
        this.plugin = plugin;
        this.setUsage("/nickcolors");
    }

    @Override
    public void execute(CommandSource sender, String[] args) throws Exception {
        if (!sender.isPlayer()) throw new Exception("<red>Only players can execute this command!");
        User user = plugin.getUser(sender.getPlayer()); // User that executes the command

        if (!testPermissionSilent(user.getPlayer())) {
            throw new InsufficientPermissionException("<red>You do not have the permission for this command!");
        }

        if (args.length == 0) {
            StringBuilder sb = new StringBuilder("Available Colors List");
            HashMap<String, String> nickColors = plugin.getSettings().getNickColors();


            for (Map.Entry<String, String> entry : nickColors.entrySet()) {
                if (user.hasPermission("hubessentials.chat.nickcolor." + entry.getKey())) {
                    String colorToUpper = entry.getKey().substring(0, 1).toUpperCase() + entry.getKey().substring(1);
                    sb.append(String.format("\n<click:run_command:/nickcolor %s><hover:show_text:'Click to set your nickname color'>%s\u2588 <grey>- %s%s</click>", entry.getKey(), entry.getValue(), entry.getValue(), colorToUpper));
                }
            }

            user.sendMessage(sb.toString());
        } else {
            String newColor = args[0].toLowerCase(); // The new color set by the user
            HashMap<String, String> nickColors = plugin.getSettings().getNickColors();

            if (!nickColors.containsKey(newColor)) {
                throw new Exception("<red>The color " + newColor + " does not exist!");
            }

            if (!user.hasPermission("hubessentials.chat.nickcolor." + newColor)) {
                throw new InsufficientPermissionException("<red>You do not have the permission to use this nick color!");
            }

            user.updateNickname(nickColors.get(newColor) + user.getPlayer().getName());
            user.sendMessage("Nickname has been changed to ➠ " + nickColors.get(newColor) + user.getPlayer().getName());
        }


    }

    @Override
    public List<String> onTabComplete(CommandSource sender, String[] args) {
        if (!sender.isPlayer()) return Collections.emptyList();
        User user = plugin.getUser(sender.getPlayer());

        if (!testPermissionSilent(user.getPlayer())) {
            return Collections.emptyList();
        }

        if (args.length == 1) {
            return plugin.getSettings().getNickColors().keySet().stream().filter(color -> user.hasPermission("hubessentials.chat.nickcolor." + color)).toList();
        }

        return Collections.emptyList();
    }

}
