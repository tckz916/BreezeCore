package com.github.tckz916.breezecore.command;

import com.github.tckz916.breezecore.BreezeCoreManager;
import com.github.tckz916.breezecore.api.BaseCommand;
import com.github.tckz916.breezecore.manager.JsonManager;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URL;

/**
 * Created by tckz916 on 2015/10/07.
 */
public class RecentstatsCommand extends BaseCommand {

    private BreezeCoreManager breezeCoreManager = BreezeCoreManager.getInstance();

    private JsonManager jsonManager = breezeCoreManager.getJsonManager();

    public static final String PERMISSION = "breezecore.command.recentstats";

    public static final String DESCRIPTION = "Recentstats Command";

    public static final String USAGE = "\n/recentstats" +
            "\n/recentstats <player>";

    public RecentstatsCommand(CommandSender sender) {
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
        if (args.length > 1) {
            sendUsage();
            return;
        }
        Player player = (Player) sender;

        String json = null;
        if (args.length == 0) {
            try {
                json = jsonManager.getJson(new URL("https://pvp.minecraft.jp/" + player.getName() + ".json"));
            } catch (IOException e) {
                e.printStackTrace();
                sender.sendMessage(format(false, "error.connect"));
                return;
            }
        } else {
            try {
                json = jsonManager.getJson(new URL("https://pvp.minecraft.jp/" + args[0] + ".json"));
            } catch (IOException e) {
                e.printStackTrace();
                sender.sendMessage(format(false, "error.connect"));
                return;
            }
        }

        JsonObject jsonObject = jsonManager.createJsonObject(json);

        JsonArray matches = jsonObject.getAsJsonObject("player").getAsJsonObject("Player").getAsJsonArray("matches");
        if (jsonObject.getAsJsonObject("player").getAsJsonObject("Player").get("last_login") == null) {
            sender.sendMessage(coloring("$cプレイヤーが見つかりません。"));
            return;
        }

        int kill = 0;
        int death = 0;
        int envdeath = 0;
        int win = 0;
        int lose = 0;
        int draw = 0;
        int count = 0;
        if (matches == null) {
            sender.sendMessage(coloring("$7----- $bRecentstats $7-----"));
            sender.sendMessage(coloring("$7直近$b" + count + "$7試合の統計"));
            sender.sendMessage(coloring("$7K: $b" + kill + " $7D: $b" + death));
            sender.sendMessage(coloring("$7K/K: $b" + 0 + " $7K/D: $b" + 0));
            sender.sendMessage(coloring("$7Win: $b" + win + " $7Lose: $b" + lose + " $7Draw: $b" + draw));
            sender.sendMessage(coloring("$7勝率: $b" + 0 + "$7%" + " $7敗率: $b" + 0 + "$7%"));
        } else {
            for (int i = 0; i < matches.size(); i++) {
                JsonObject object = matches.get(i).getAsJsonObject();
                if (!((object.get("gamemode").getAsString().equals("paintball")) || (object.get("gamemode").getAsString().equals("splatt")) || (object.get("gamemode").getAsString().equals("blitz")))) {
                    int kill_count = object.get("kill_count").getAsInt();
                    kill += kill_count;
                    int death_count = object.get("death_count").getAsInt();
                    death += death_count;
                    int envdeath_count = object.get("envdeath_count").getAsInt();
                    envdeath += envdeath_count;
                    switch (object.get("result").getAsString()) {
                        case "win":
                            win++;
                            break;
                        case "lose":
                            lose++;
                            break;
                        case "draw":
                            draw++;
                            break;
                    }
                    count++;
                }
            }

            double kk = divide(kill, death);
            double kd = divide(kill, death + envdeath);

            double win_percentage = win_lose_percentage(win, lose);
            double lose_percentage = win_lose_percentage(lose, win);

            sender.sendMessage(coloring("$7----- $bRecentstats $7-----"));
            sender.sendMessage(coloring("$7直近$b" + count + "$7試合の統計"));
            sender.sendMessage(coloring("$7K: $b" + kill + " $7D: $b" + death));
            sender.sendMessage(coloring("$7K/K: $b" + kk + " $7K/D: $b" + kd));
            sender.sendMessage(coloring("$7Win: $b" + win + " $7Lose: $b" + lose + " $7Draw: $b" + draw));
            sender.sendMessage(coloring("$7勝率: $b" + win_percentage + " $7敗率: $b" + lose_percentage));
        }


    }

    private String coloring(String message) {
        return breezeCoreManager.getMessageFormat().coloring(message);
    }

    private String format(boolean prefix, String key, Object... args) {
        return breezeCoreManager.getMessageFormat().format(prefix, key, args);
    }

    private double divide(double d1, double d2) {
        BigDecimal b = new BigDecimal(d1);
        BigDecimal b1 = new BigDecimal(d2);
        BigDecimal b2 = b.divide(b1, 3, BigDecimal.ROUND_HALF_UP);
        return b2.doubleValue();
    }

    private double win_lose_percentage(double d1, double d2) {
        BigDecimal b1 = new BigDecimal(d1);
        BigDecimal b2 = new BigDecimal(d2);
        BigDecimal b1_b2_total = b1.add(b2).divide(new BigDecimal(100));
        BigDecimal percentage = b1.divide(b1_b2_total, 1, BigDecimal.ROUND_HALF_UP);

        return percentage.doubleValue();
    }
}
