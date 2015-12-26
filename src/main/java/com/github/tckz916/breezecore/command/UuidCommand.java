package com.github.tckz916.breezecore.command;

import com.github.tckz916.breezecore.BreezeCore;
import com.github.tckz916.breezecore.BreezeCoreManager;
import com.github.tckz916.breezecore.api.BaseCommand;
import com.github.tckz916.breezecore.manager.FileManager;
import com.github.tckz916.breezecore.manager.JsonManager;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.io.IOException;
import java.net.URL;

/**
 * Created by tckz916 on 2015/10/09.
 */
public class UuidCommand extends BaseCommand {

    private BreezeCore plugin = BreezeCore.getInstance();
    private BreezeCoreManager breezeCoreManager = BreezeCoreManager.getInstance();

    private FileManager fileManager = breezeCoreManager.getFileManager();

    private JsonManager jsonManager = breezeCoreManager.getJsonManager();

    public static final String PERMISSION = "breezecore.command.uuid";

    public static final String DESCRIPTION = "Uuid Command";

    public static final String USAGE = "/Uuid <player>";

    public UuidCommand(CommandSender sender) {
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
        if (args.length < 1 || args.length > 1) {
            sendUsage();
            return;
        }
        if (!args[0].matches("[A-Za-z0-9_]{2,16}")) {
            sender.sendMessage(format(false, "error.player-not-found"));
            return;
        }
        String json = null;
        if (args.length > 0 || args.length < 2) {
            try {
                json = jsonManager.getJson(new URL("http://mcuuid.com/api/" + args[0]));
            } catch (IOException e) {
                e.printStackTrace();
                sender.sendMessage(format(false, "error.connect"));
                return;
            }
        }


        JsonObject jsonObject = new Gson().fromJson(json, JsonObject.class);

        if (jsonObject.get("name") == null) {
            sender.sendMessage(format(false, "error.player-not-found"));
            return;
        }

        String uuid = jsonObject.get("uuid").getAsString();
        String uuid_formatted = jsonObject.get("uuid_formatted").getAsString();
        String name = jsonObject.get("name").getAsString();


        sender.sendMessage(coloring("$7----- $bMinecraft UUID $7-----"));
        sender.sendMessage(coloring("$b" + name + "$7のUUID"));
        sender.sendMessage(coloring("$7UUID: $b" + uuid));
        sender.sendMessage(coloring("$7Format_UUID: $b" + uuid_formatted));

        File file = fileManager.loadFile(plugin.getDataFolder().toString(), "uuid.txt");
        fileManager.writeFile(file, name + ": " + " uuid: " + uuid + " uuid_formatted: " + uuid_formatted);

    }

    private String coloring(String message) {
        return breezeCoreManager.getMessageFormat().coloring(message);
    }

    private String format(boolean prefix, String key, Object... args) {
        return breezeCoreManager.getMessageFormat().format(prefix, key, args);
    }
}
