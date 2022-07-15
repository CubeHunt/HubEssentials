package net.cubehunt.hubessentials.commands;

import net.cubehunt.hubessentials.HubEssentials;

import java.util.List;

public class NicknameCommand extends BaseCommand {

    private final HubEssentials plugin;

    public NicknameCommand(HubEssentials plugin) {
        super("nickname", "Change your nickname", "hubessentials.nickname");
        this.plugin = plugin;
        this.setUsage("/nickname <nickname> [player]");
    }

    @Override
    public void execute(CommandSource sender, String[] args) throws Exception {

    }

    @Override
    public List<String> onTabComplete(CommandSource sender, String[] args) {
        return null;
    }
}
