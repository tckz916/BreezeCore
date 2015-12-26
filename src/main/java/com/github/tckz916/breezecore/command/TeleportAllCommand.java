package com.github.tckz916.breezecore.command;

import com.github.tckz916.breezecore.BreezeCoreManager;
import com.github.tckz916.breezecore.api.BaseCommand;
import java.util.Iterator;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportAllCommand extends BaseCommand {

    private BreezeCoreManager breezeCoreManager = BreezeCoreManager.getInstance();
    public static final String PERMISSION = "breezecore.command.tp";
    public static final String DESCRIPTION = "Teleport Command";
    public static final String USAGE = "\n/tpa\n/tpa <player>";

    public TeleportAllCommand(CommandSender sender) {
        super(sender, "breezecore.command.tp", "Teleport Command", "\n/tpa\n/tpa <player>");
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
            String message = null;

            switch (args.length) {
            case 0:
                Iterator target1 = Bukkit.getServer().getOnlinePlayers().iterator();

                while (target1.hasNext()) {
                    Player players2 = (Player) target1.next();

                    players2.teleport(player);
                }

                message = this.format(true, "message.teleport", new Object[0]).replace("%from%", "OnlinePlayers").replace("%to%", player.getDisplayName());
                this.breezeCoreManager.getMessageFormat().broadcastMessage(message);
                break;

            case 1:
                Player target = Bukkit.getPlayer(args[0]);

                if (target == null) {
                    sender.sendMessage(this.format(false, "error.player-not-found", new Object[0]));
                    return;
                }

                Iterator players = Bukkit.getServer().getOnlinePlayers().iterator();

                while (players.hasNext()) {
                    Player players1 = (Player) players.next();

                    players1.teleport(target);
                }

                message = this.format(true, "message.teleport", new Object[0]).replace("%from%", "OnlinePlayers").replace("%to%", target.getDisplayName());
                this.breezeCoreManager.getMessageFormat().broadcastMessage(message);
            }

        }
    }

    private String format(boolean prefix, String key, Object... args) {
        return this.breezeCoreManager.getMessageFormat().format(prefix, key, args);
    }
}
