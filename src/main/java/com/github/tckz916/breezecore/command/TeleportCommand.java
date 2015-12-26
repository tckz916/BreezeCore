package com.github.tckz916.breezecore.command;

import com.github.tckz916.breezecore.BreezeCoreManager;
import com.github.tckz916.breezecore.api.BaseCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TeleportCommand extends BaseCommand {

    private BreezeCoreManager breezeCoreManager = BreezeCoreManager.getInstance();
    public static final String PERMISSION = "breezecore.command.tp";
    public static final String DESCRIPTION = "Teleport Command";
    public static final String USAGE = "\n/tp <player>\n/tp <player> <player>\n/tp <x> <y> <z>\n/tp <player> <x> <y> <z>\n/tp <x> <y> <z> <yaw> <pitch>\n/tp <player> <x> <y> <z> <yaw> <pitch>";

    public TeleportCommand(CommandSender sender) {
        super(sender, "breezecore.command.tp", "Teleport Command", "\n/tp <player>\n/tp <player> <player>\n/tp <x> <y> <z>\n/tp <player> <x> <y> <z>\n/tp <x> <y> <z> <yaw> <pitch>\n/tp <player> <x> <y> <z> <yaw> <pitch>");
    }

    public void execute(CommandSender sender, Command command, String label, String[] args) {
        if (!this.hasPermission()) {
            sender.sendMessage(this.format(false, "error.no-permission", new Object[0]));
        } else if (this.isSenderConsole()) {
            sender.sendMessage(this.format(false, "error.console", new Object[0]));
        } else if (this.isSenderRemoteConsole()) {
            sender.sendMessage(this.format(false, "error.console", new Object[0]));
        } else if (args.length >= 1 && args.length <= 6) {
            Player player = (Player) sender;
            Player target = null;
            String message = null;
            World e;
            double x;
            double y;
            double z;
            double yaw;
            double pitch;
            Location loc;
            Location yaw1;

            switch (args.length) {
            case 1:
                target = Bukkit.getPlayer(args[0]);
                if (target == null) {
                    sender.sendMessage(this.format(false, "error.player-not-found", new Object[0]));
                    return;
                }

                player.teleport(target);
                message = this.format(true, "message.teleport", new Object[0]).replace("%from%", player.getDisplayName()).replace("%to%", target.getDisplayName());
                sender.sendMessage(message);
                sender.sendMessage(message);
                break;

            case 2:
                target = Bukkit.getPlayer(args[0]);
                Player to = Bukkit.getPlayer(args[1]);

                if (target == null || to == null) {
                    sender.sendMessage(this.format(false, "error.player-not-found", new Object[0]));
                    return;
                }

                target.teleport(to);
                message = this.format(true, "message.teleport", new Object[0]).replace("%from%", target.getDisplayName()).replace("%to%", to.getDisplayName());
                sender.sendMessage(message);
                sender.sendMessage(message);
                break;

            case 3:
                try {
                    e = player.getWorld();
                    x = Double.parseDouble(args[0]);
                    y = Double.parseDouble(args[1]);
                    z = Double.parseDouble(args[2]);
                    yaw1 = new Location(e, x, y, z);
                    player.teleport(yaw1);
                    message = this.format(true, "message.teleport", new Object[0]).replace("%from%", player.getDisplayName()).replace("%to%", "" + x + ", " + y + ", " + z);
                    sender.sendMessage(message);
                } catch (NumberFormatException numberformatexception) {
                    this.sendUsage();
                    System.out.print(numberformatexception);
                }
                break;

            case 4:
                try {
                    target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        sender.sendMessage(this.format(false, "error.player-not-found", new Object[0]));
                        return;
                    }

                    e = player.getWorld();
                    x = Double.parseDouble(args[1]);
                    y = Double.parseDouble(args[2]);
                    z = Double.parseDouble(args[3]);
                    yaw1 = new Location(e, x, y, z);
                    target.teleport(yaw1);
                    message = this.format(true, "message.teleport", new Object[0]).replace("%from%", target.getDisplayName()).replace("%to%", "" + x + ", " + y + ", " + z);
                    sender.sendMessage(message);
                    sender.sendMessage(message);
                } catch (NumberFormatException numberformatexception1) {
                    this.sendUsage();
                    System.out.print(numberformatexception1);
                }
                break;

            case 5:
                try {
                    e = player.getWorld();
                    x = Double.parseDouble(args[0]);
                    y = Double.parseDouble(args[1]);
                    z = Double.parseDouble(args[2]);
                    yaw = Double.parseDouble(args[3]);
                    pitch = Double.parseDouble(args[4]);
                    loc = new Location(e, x, y, z, (float) yaw, (float) pitch);
                    player.teleport(loc);
                    message = this.format(true, "message.teleport", new Object[0]).replace("%from%", player.getDisplayName()).replace("%to%", "" + x + ", " + y + ", " + z + ", " + yaw + ", " + pitch);
                    sender.sendMessage(message);
                } catch (NumberFormatException numberformatexception2) {
                    this.sendUsage();
                    System.out.print(numberformatexception2);
                }
                break;

            case 6:
                try {
                    target = Bukkit.getPlayer(args[0]);
                    if (target == null) {
                        sender.sendMessage(this.format(false, "error.player-not-found", new Object[0]));
                        return;
                    }

                    e = player.getWorld();
                    x = Double.parseDouble(args[1]);
                    y = Double.parseDouble(args[2]);
                    z = Double.parseDouble(args[3]);
                    yaw = Double.parseDouble(args[4]);
                    pitch = Double.parseDouble(args[5]);
                    loc = new Location(e, x, y, z, (float) yaw, (float) pitch);
                    target.teleport(loc);
                    message = this.format(true, "message.teleport", new Object[0]).replace("%from%", target.getDisplayName()).replace("%to%", "" + x + ", " + y + ", " + z + ", " + yaw + ", " + pitch);
                    sender.sendMessage(message);
                    sender.sendMessage(message);
                } catch (NumberFormatException numberformatexception3) {
                    this.sendUsage();
                    System.out.print(numberformatexception3);
                }
            }

        } else {
            this.sendUsage();
        }
    }

    private String format(boolean prefix, String key, Object... args) {
        return this.breezeCoreManager.getMessageFormat().format(prefix, key, args);
    }
}
