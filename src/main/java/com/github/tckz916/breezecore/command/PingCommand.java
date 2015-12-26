package com.github.tckz916.breezecore.command;

import com.github.tckz916.breezecore.BreezeCoreManager;
import com.github.tckz916.breezecore.api.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class PingCommand extends BaseCommand {

    private BreezeCoreManager breezeCoreManager = BreezeCoreManager.getInstance();
    public static final String PERMISSION = "breezecore.command.ping";
    public static final String DESCRIPTION = "Ping Command";
    public static final String USAGE = "/ping <player>";

    public PingCommand(CommandSender sender) {
        super(sender, "breezecore.command.ping", "Ping Command", "/ping <player>");
    }

    public void execute(CommandSender sender, Command command, String label, String[] args) {
        if (!this.hasPermission()) {
            sender.sendMessage(this.format(false, "error.no-permission", new Object[0]));
        } else if (this.isSenderConsole()) {
            sender.sendMessage(this.format(false, "error.console", new Object[0]));
        } else if (this.isSenderRemoteConsole()) {
            sender.sendMessage(this.format(false, "error.console", new Object[0]));
        } else if (args.length > 1) {
            this.sendUsage();
        } else {
            Player player = (Player) sender;

            if (args.length == 0) {
                sender.sendMessage(this.coloring("$7Ping: $b" + this.getPing(player) + "."));
            } else {
                Player target = Bukkit.getServer().getPlayer(args[0]);

                if (target != null) {
                    sender.sendMessage(this.coloring("$7" + target.getName() + "\'s Ping: $b" + this.getPing(target) + "."));
                } else {
                    sender.sendMessage(this.format(false, "error.player-not-found", new Object[0]));
                }
            }

        }
    }

    private int getPing(Player player) {
        return ((CraftPlayer) player).getHandle().ping;
    }

    private String format(boolean prefix, String key, Object... args) {
        return this.breezeCoreManager.getMessageFormat().format(prefix, key, args);
    }

    private String coloring(String msg) {
        return this.breezeCoreManager.getMessageFormat().coloring(msg);
    }
}
