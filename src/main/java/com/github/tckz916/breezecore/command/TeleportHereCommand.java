package com.github.tckz916.breezecore.command;

import com.github.tckz916.breezecore.BreezeCore;
import com.github.tckz916.breezecore.BreezeCoreManager;
import com.github.tckz916.breezecore.api.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportHereCommand extends BaseCommand {

    private BreezeCore plugin = BreezeCore.getInstance();
    private BreezeCoreManager breezeCoreManager = BreezeCoreManager.getInstance();
    public static final String PERMISSION = "breezecore.command.tp";
    public static final String DESCRIPTION = "Teleport Command";
    public static final String USAGE = "/tphere <player>";

    public TeleportHereCommand(CommandSender sender) {
        super(sender, "breezecore.command.tp", "Teleport Command", "/tphere <player>");
    }

    public void execute(CommandSender sender, Command command, String label, String[] args) {
        if (!this.hasPermission()) {
            sender.sendMessage(this.format(false, "error.no-permission", new Object[0]));
        } else if (this.isSenderConsole()) {
            sender.sendMessage(this.format(false, "error.console", new Object[0]));
        } else if (this.isSenderRemoteConsole()) {
            sender.sendMessage(this.format(false, "error.console", new Object[0]));
        } else if (args.length >= 1 && args.length <= 2) {
            Player player = (Player) sender;
            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage(this.format(false, "error.player-not-found", new Object[0]));
            } else {
                target.teleport(player);
                String message = this.format(true, "message.teleport", new Object[0]).replace("%from%", target.getDisplayName()).replace("%to%", player.getDisplayName());

                sender.sendMessage(message);
                sender.sendMessage(message);
            }
        } else {
            this.sendUsage();
        }
    }

    private String format(boolean prefix, String key, Object... args) {
        return this.breezeCoreManager.getMessageFormat().format(prefix, key, args);
    }
}
