package com.github.tckz916.breezecore.command;

import com.github.tckz916.breezecore.BreezeCore;
import com.github.tckz916.breezecore.BreezeCoreManager;
import com.github.tckz916.breezecore.api.BaseCommand;
import com.github.tckz916.breezecore.manager.FileManager;
import com.github.tckz916.breezecore.manager.JsonManager;
import com.google.gson.JsonObject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.IOException;

/**
 * Created by tckz916 on 2015/10/14.
 */
public class PrefixCommand extends BaseCommand {
    private BreezeCore plugin = BreezeCore.getInstance();
    private BreezeCoreManager breezeCoreManager = BreezeCoreManager.getInstance();

    private FileManager fileManager = breezeCoreManager.getFileManager();

    private JsonManager jsonManager = breezeCoreManager.getJsonManager();
    public static final String PERMISSION = "breezecore.command.prefix";

    public static final String DESCRIPTION = "Prefix Command";

    public static final String USAGE = "/prefix <prefix>";

    public PrefixCommand(CommandSender sender) {
        super(sender, PERMISSION, DESCRIPTION, USAGE);
    }

    @Override
    public void execute(CommandSender sender, Command command, String label, String[] args) {

        if (!hasPermission()) {
            sender.sendMessage(format(false, "error.no-permission"));
            return;
        }
        if (isSenderConsole()) {
            sender.sendMessage(format(false, "error.console"));
            return;
        }
        if (isSenderRemoteConsole()) {
            sender.sendMessage(format(false, "error.console"));
            return;
        }
        if (args.length < 1) {
            sendUsage();
            return;
        }

        plugin.reloadConfig();

        FileConfiguration fileConfiguration = plugin.getConfig();

        ConfigurationSection prefix = fileConfiguration.getConfigurationSection("prefix");

        switch (args[0]) {
            case "add":
                if (args.length < 3) {
                    sender.sendMessage(coloring("$7- $bUsage: $7/prefix add <name> <prefix>"));
                    return;
                }
                if (prefix.getKeys(false).contains(args[1])) {
                    sender.sendMessage(format(false, "error.already-exists"));
                    return;
                }
                fileConfiguration.set("prefix." + args[1], build(args, 2).replace("#s", " "));
                plugin.saveConfig();
                sender.sendMessage(format(true, "message.prefix.add").replace("%prefix%", args[1]));
                break;
            case "delete":
                if (args.length < 2) {
                    sender.sendMessage(coloring("$7- $bUsage: $7/prefix delete <name>"));
                    return;
                }
                fileConfiguration.set("prefix." + args[1], null);
                plugin.saveConfig();
                sender.sendMessage(format(true, "message.prefix.delete").replace("%prefix%", args[1]));
                break;
            case "get":
                if (args.length < 2) {
                    sender.sendMessage(coloring("$7- $bUsage: $7/prefix get <name>"));
                    return;
                }
                if (!prefix.getKeys(false).contains(args[1])) {
                    sender.sendMessage(format(false, "error.prefix-not-found"));
                    return;
                }
                String name = fileConfiguration.getString("prefix." + args[1]);
                sender.sendMessage(
                        format(true, "message.prefix.get", args[1])
                                .replace("%prefix%", coloring(name)));
                break;
            case "list":
                if (args.length > 1) {
                    sender.sendMessage(coloring("$7- $bUsage: $7/prefix list"));
                    return;
                }
                sender.sendMessage(coloring("$7------- [$b Prefix List $7] -------"));
                for (String s : prefix.getKeys(false)) {
                    sender.sendMessage(
                            format(false, "message.prefix.list", s)
                                    .replace("%prefix%", coloring(fileConfiguration.getString(prefix.getName() + "." + s))));
                }
                sender.sendMessage(format(false, "message.prefix.help"));
                break;
            case "rename":
                if (args.length < 3) {
                    sender.sendMessage(coloring("$7- $bUsage: $7/prefix rename <name> <prefix>"));
                    return;
                }
                if (!prefix.getKeys(false).contains(args[1])) {
                    sender.sendMessage(format(false, "error.prefix-not-found"));
                    return;
                }
                fileConfiguration.set("prefix." + args[1], build(args, 2).replace("#s", " "));
                plugin.saveConfig();
                sender.sendMessage(
                        format(true, "message.prefix.rename")
                                .replace("%prefix%", args[1]));
                break;
            case "reset":
                if (args.length < 2) {
                    sender.sendMessage(coloring("$7- $bUsage: $7/prefix reset <player>"));
                    return;
                }
                if (getPlayer(args[1]) == null) {
                    sender.sendMessage(format(false, "error.player-not-found"));
                    return;
                }
                getPlayer(args[1]).setDisplayName(getPlayer(args[1]).getName());
                setprefix(getPlayer(args[1]), "");
                sender.sendMessage(format(true, "message.prefix.reset")
                        .replace("%player%", getPlayer(args[1]).getName()));
                break;
            case "set":
                if (args.length < 3) {
                    sender.sendMessage(coloring("$7- $bUsage: $7/prefix set <name> <player>"));
                    return;
                }
                if (!prefix.getKeys(false).contains(args[1])) {
                    sender.sendMessage(format(false, "error.already-prefix"));
                    return;
                }
                if (getPlayer(args[2]) == null) {
                    sender.sendMessage(format(false, "error.player-not-found"));
                    return;
                }
                String pn = fileConfiguration.getString("prefix." + args[1]);
                getPlayer(args[2]).setDisplayName(coloring(pn + "$r" + getPlayer(args[2]).getName()));
                setprefix(getPlayer(args[2]), pn);

                sender.sendMessage(format(true, "message.prefix.set")
                        .replace("%prefix%", args[1])
                        .replace("%player%", getPlayer(args[2]).getName()));
                break;
            default:
                sendUsage();
                break;
        }


    }

    private Player getPlayer(String name) {
        return plugin.getServer().getPlayer(name);
    }

    private void setprefix(Player player, String prefix) {
        File file = fileManager.loadFile(plugin.getDataFolder().toString() + "\\players", player.getUniqueId().toString() + ".json");
        String json = null;
        try {
            json = jsonManager.getJson(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
        JsonObject playerdata = jsonManager.createJsonObject(json);

        playerdata.getAsJsonObject("Player").addProperty("prefix", prefix);

        String json1 = jsonManager.toJson(playerdata);

        player.setDisplayName(coloring(prefix + player.getName()));
        jsonManager.writeJson(file, json1);
    }

    private String build(String[] strings, int start) {
        return breezeCoreManager.getMessageFormat().build(strings, start);
    }

    private String coloring(String message) {
        return breezeCoreManager.getMessageFormat().coloring(message);
    }

    private String format(boolean prefix, String key, Object... args) {
        return breezeCoreManager.getMessageFormat().format(prefix, key, args);
    }
}
