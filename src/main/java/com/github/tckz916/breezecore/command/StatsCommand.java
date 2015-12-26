package com.github.tckz916.breezecore.command;

import com.github.tckz916.breezecore.BreezeCore;
import com.github.tckz916.breezecore.BreezeCoreManager;
import com.github.tckz916.breezecore.api.BaseCommand;
import com.github.tckz916.breezecore.manager.JsonManager;
import com.google.gson.Gson;
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
public class StatsCommand extends BaseCommand {

    private BreezeCore plugin = BreezeCore.getInstance();
    private BreezeCoreManager breezeCoreManager = BreezeCoreManager.getInstance();

    private JsonManager jsonManager = breezeCoreManager.getJsonManager();

    public static final String PERMISSION = "breezecore.command.stats";

    public static final String DESCRIPTION = "Stats Command";

    public static final String USAGE = "\n/stats" +
            "\n/stats <player>";

    public StatsCommand(CommandSender sender) {
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

        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);

        JsonObject teampvp = jsonObject.getAsJsonObject("player").getAsJsonObject("Player").getAsJsonObject("teampvp");
        if (jsonObject.getAsJsonObject("player").getAsJsonObject("Player").get("last_login") == null) {
            sender.sendMessage(coloring("$cプレイヤーが見つかりません。"));
            return;
        }

        int kill = 0;
        int death = 0;
        double kk = teampvp.get("kk_ratio").getAsDouble();
        double kd = teampvp.get("kd_ratio").getAsDouble();

        if (teampvp.get("kill_count") == null || teampvp.get("death_count") == null) {
            sender.sendMessage(sendStats(kill,death,kk,kd));
        } else {
            kill = teampvp.get("kill_count").getAsInt();
            death = teampvp.get("death_count").getAsInt();
            double kk1 = setScale(kk);
            double kd1 = setScale(kd);
            sender.sendMessage(sendStats(kill,death,kk1,kd1));
        }

    }

    private String coloring(String message) {
        return breezeCoreManager.getMessageFormat().coloring(message);
    }

    private String format(boolean prefix, String key, Object... args) {
        return breezeCoreManager.getMessageFormat().format(prefix, key, args);
    }

    private String  sendStats(int kill,int death,double kk,double kd) {
        return coloring("$7----- $bStats $7-----" +
                "\n$7K: $b" + kill + " $7D: $b" + death +
                "\n$7K/K: $b" + kk + " $7K/D: $b" + kd);
    }

    private double setScale(double d) {
        BigDecimal b = new BigDecimal(d);
        BigDecimal b1 = b.setScale(3, BigDecimal.ROUND_HALF_UP);
        return b1.doubleValue();
    }

}
