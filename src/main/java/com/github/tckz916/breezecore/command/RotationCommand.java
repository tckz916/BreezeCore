package com.github.tckz916.breezecore.command;

import com.github.tckz916.breezecore.BreezeCoreManager;
import com.github.tckz916.breezecore.api.BaseCommand;
import com.github.tckz916.breezecore.type.RotationType;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by tckz916 on 2015/10/11.
 */
public class RotationCommand extends BaseCommand {

    private BreezeCoreManager breezeCoreManager = BreezeCoreManager.getInstance();

    public static final String PERMISSION = "breezecore.command.rotation";

    public static final String DESCRIPTION = "Rotation Command";

    public static final String USAGE = "/rotation <server>";

    public RotationCommand(CommandSender sender) {
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
        List<String> rotationlist = new ArrayList<>();
        for (RotationType rotationType : RotationType.values()) {
            rotationlist.add(rotationType.toString());
        }
        List<String> text = null;
        try {
            if (!(rotationlist.contains(args[0]))) {
                sendUsage();
                return;
            }
            text = getText(new URL("https://maps.minecraft.jp/production/rotations/" + RotationType.valueOf(args[0]).getString() + ".txt"));
        } catch (IOException e) {
            e.printStackTrace();
            sender.sendMessage(format(false, "error.connect"));
            return;
        }

        sender.sendMessage(coloring("$7----- $bRotation $7-----"));
        for (int i = 0; i < text.size(); i++) {
            int count = i + 1;
            sender.sendMessage(coloring("$7" + count + ". $b" + text.get(i)));
        }
    }

    private String coloring(String message) {
        return breezeCoreManager.getMessageFormat().coloring(message);
    }

    private String format(boolean prefix, String key, Object... args) {
        return breezeCoreManager.getMessageFormat().format(prefix, key, args);
    }

    private List<String> getText(URL url) throws IOException {
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("User-agent", "Mozilla/5.0");
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        List<String> strings = new ArrayList<>();

        String line = null;
        while ((line = reader.readLine()) != null) {
            strings.add(line);
        }
        reader.close();

        return strings;
    }
}
