package com.github.tckz916.breezecore.command;

import com.github.tckz916.breezecore.BreezeCore;
import com.github.tckz916.breezecore.BreezeCoreManager;
import com.github.tckz916.breezecore.api.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.metadata.FixedMetadataValue;

public class PrivateMessageCommand extends BaseCommand {

    private BreezeCore plugin = BreezeCore.getInstance();
    private BreezeCoreManager breezeCoreManager = BreezeCoreManager.getInstance();
    public static final String PERMISSION = "breezecore.command.tell";
    public static final String DESCRIPTION = "PrivateMessage Command";
    public static final String USAGE = "/tell <player> <message>";

    public PrivateMessageCommand(CommandSender sender) {
        super(sender, "breezecore.command.tell", "PrivateMessage Command", "/tell <player> <message>");
    }

    public void execute(CommandSender sender, Command command, String label, String[] args) {
        if (!this.hasPermission()) {
            sender.sendMessage(this.format(false, "error.no-permission", new Object[0]));
        } else if (this.isSenderConsole()) {
            sender.sendMessage(this.format(false, "error.console", new Object[0]));
        } else if (this.isSenderRemoteConsole()) {
            sender.sendMessage(this.format(false, "error.console", new Object[0]));
        } else if (args.length < 2) {
            this.sendUsage();
        } else {
            Player player = (Player) sender;
            Player target = Bukkit.getPlayer(args[0]);

            if (target == null) {
                sender.sendMessage(this.format(false, "error.player-not-found", new Object[0]));
            } else {
                String msg = this.build(args, 1);
                String fromformat = this.plugin.getConfig().getString("privatemessage.fromformat").replace("%player%", target.getDisplayName()).replace("%message%", msg);
                String toformat = this.plugin.getConfig().getString("privatemessage.toformat").replace("%player%", player.getDisplayName()).replace("%message%", msg);

                sender.sendMessage(this.coloring(fromformat));
                sender.sendMessage(this.coloring(toformat));
                target.setMetadata("reply", new FixedMetadataValue(this.plugin, player));
            }
        }
    }

    private String format(boolean prefix, String key, Object... args) {
        return this.breezeCoreManager.getMessageFormat().format(prefix, key, args);
    }

    private String coloring(String msg) {
        return this.breezeCoreManager.getMessageFormat().coloring(msg);
    }

    private String build(String[] strings, int start) {
        return this.breezeCoreManager.getMessageFormat().build(strings, start);
    }
}
