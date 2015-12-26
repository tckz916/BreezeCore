package com.github.tckz916.breezecore.command.tabcompete;


import com.github.tckz916.breezecore.BreezeCore;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by tckz916 on 2015/09/22.
 */
public class PrefixTabComple implements TabCompleter {

    private BreezeCore plugin = BreezeCore.getInstance();

    private static final String[] COMMANDS = {"add", "delete", "list", "get", "set", "reset","rename"};

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }

        List<String> list = new ArrayList<>();

        ConfigurationSection prefix = plugin.getConfig().getConfigurationSection("prefix");

        switch (args.length) {
            case 1:
                List<String> commands = new ArrayList<>(Arrays.asList(COMMANDS));
                StringUtil.copyPartialMatches(args[0], commands, list);
                break;
            case 2:
                if (args[0].equals("delete") || args[0].equals("get") || args[0].equals("set") || args[0].equals("rename")) {
                    List<String> prefixs = new ArrayList<>();
                    for (String s : prefix.getKeys(false)) {
                        prefixs.add(s);
                    }
                    StringUtil.copyPartialMatches(args[1], prefixs, list);
                }
                if (args[0].equals("reset")) {
                    List<String> players = new ArrayList<>();
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        players.add(player.getName());
                    }
                    StringUtil.copyPartialMatches(args[1], players, list);
                }
                break;
            case 3:
                if (args[0].equals("set")) {
                    List<String> players = new ArrayList<>();
                    for (Player player : Bukkit.getServer().getOnlinePlayers()) {
                        players.add(player.getName());
                    }
                    StringUtil.copyPartialMatches(args[2], players, list);
                }
                break;
        }

        Collections.sort(list);

        return list;
    }
}
