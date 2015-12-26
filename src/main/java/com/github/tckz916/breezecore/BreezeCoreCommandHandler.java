package com.github.tckz916.breezecore;

import com.github.tckz916.breezecore.api.BaseCommand;
import com.github.tckz916.breezecore.command.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

/**
 * Created by tckz916 on 2015/10/05.
 */
public class BreezeCoreCommandHandler implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        BaseCommand cmd = new HelpCommand(sender);

        switch (command.getName()) {
            case "breezecore":
                cmd = new HelpCommand(sender);
                break;
            case "stats":
                cmd = new StatsCommand(sender);
                break;
            case "recentstats":
                cmd = new RecentstatsCommand(sender);
                break;
            case "uuid":
                cmd = new UuidCommand(sender);
                break;
            case "rotation":
                cmd = new RotationCommand(sender);
                break;
            case "prefix":
                cmd = new PrefixCommand(sender);
                break;
        }
        cmd.execute(sender, command, label, args);
        return true;
    }
}
