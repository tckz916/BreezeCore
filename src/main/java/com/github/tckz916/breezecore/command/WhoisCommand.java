package com.github.tckz916.breezecore.command;

import com.github.tckz916.breezecore.BreezeCore;
import com.github.tckz916.breezecore.BreezeCoreManager;
import com.github.tckz916.breezecore.api.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WhoisCommand extends BaseCommand {

    private BreezeCore plugin = BreezeCore.getInstance();
    private BreezeCoreManager breezeCoreManager = BreezeCoreManager.getInstance();
    public static final String PERMISSION = "breezecore.command.whois";
    public static final String DESCRIPTION = "Whois Command";
    public static final String USAGE = "\n/whois\n/whois <player>";

    public WhoisCommand(CommandSender sender) {
        super(sender, "breezecore.command.whois", "Whois Command", "\n/whois\n/whois <player>");
    }

    public void execute(CommandSender sender, Command command, String label, String[] args) {
        if (!this.hasPermission()) {
            sender.sendMessage(this.format(false, "error.no-permission", new Object[0]));
        } else if (this.isSenderConsole()) {
            sender.sendMessage(this.format(false, "error.console", new Object[0]));
        } else if (this.isSenderRemoteConsole()) {
            sender.sendMessage(this.format(false, "error.console", new Object[0]));
        } else if (args.length > 2) {
            this.sendUsage();
        } else {
            Player player = (Player) sender;

            switch (args.length) {
            case 0:
                sender.sendMessage(this.coloring("$7PlayerName: $b" + player.getName()));
                sender.sendMessage(this.coloring("$7Language: $b" + player.spigot().getLocale()));
                sender.sendMessage(this.coloring("$7Address: $b" + player.getAddress().getAddress().getHostAddress()));
                sender.sendMessage(this.coloring("$7HostName: $b" + player.getAddress().getHostName()));
                sender.sendMessage(this.coloring("$7RawAddress: $b" + player.spigot().getRawAddress()));
                break;

            case 1:
                String name = args[0];
                Player target = Bukkit.getPlayer(name);

                if (target == null) {
                    sender.sendMessage(this.format(false, "error.player-not-found", new Object[0]));
                    return;
                }

                sender.sendMessage(this.coloring("$7PlayerName: $b" + name));
                sender.sendMessage(this.coloring("$7Language: $b" + target.spigot().getLocale()));
                sender.sendMessage(this.coloring("$7Address: $b" + target.getAddress().getAddress().getHostAddress()));
                sender.sendMessage(this.coloring("$7HostName: $b" + target.getAddress().getHostName()));
                sender.sendMessage(this.coloring("$7RawAddress: $b" + target.spigot().getRawAddress()));
            }

        }
    }

    private String format(boolean prefix, String key, Object... args) {
        return this.breezeCoreManager.getMessageFormat().format(prefix, key, args);
    }

    private String coloring(String msg) {
        return this.breezeCoreManager.getMessageFormat().coloring(msg);
    }
}
