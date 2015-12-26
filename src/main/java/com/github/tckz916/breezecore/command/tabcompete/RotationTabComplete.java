package com.github.tckz916.breezecore.command.tabcompete;

import com.github.tckz916.breezecore.type.RotationType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tckz916 on 2015/10/11.
 */
public class RotationTabComplete implements TabCompleter {

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return null;
        }

        List<String> list = new ArrayList<>();


        if (args.length == 1) {
            List<String> rotationlist = new ArrayList<>();
            for (RotationType rotationType : RotationType.values()) {
                rotationlist.add(rotationType.toString());
            }
            StringUtil.copyPartialMatches(args[0], rotationlist, list);
        }

        return list;
    }
}
