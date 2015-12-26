package com.github.tckz916.breezecore.listener;

import com.github.tckz916.breezecore.BreezeCore;
import com.github.tckz916.breezecore.BreezeCoreManager;
import com.github.tckz916.breezecore.manager.FileManager;
import com.github.tckz916.breezecore.manager.JsonManager;
import com.github.tckz916.breezecore.util.Tablist;
import com.google.gson.JsonObject;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;

/**
 * Created by tckz916 on 2015/10/07.
 */
public class PlayerListener implements Listener {

    private BreezeCore plugin = BreezeCore.getInstance();
    private BreezeCoreManager breezeCoreManager = BreezeCoreManager.getInstance();

    private FileManager fileManager = breezeCoreManager.getFileManager();
    private JsonManager jsonManager = breezeCoreManager.getJsonManager();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {

        Player player = event.getPlayer();
        FileConfiguration fileConfiguration = plugin.getConfig();

        String header = coloring(fileConfiguration.getString("customtab.header"));
        String footer = coloring(fileConfiguration.getString("customtab.footer"));

        Tablist.sendTablist(player, header, footer);

        File file = fileManager.loadFile(plugin.getDataFolder().toString() + "\\players", player.getUniqueId().toString() + ".json");
        JsonObject root = jsonManager.playerJson(file, player);
        String json1 = jsonManager.toJson(root);
        jsonManager.writeJson(file, json1);


        String prefix = root.getAsJsonObject("Player").get("prefix").getAsString();

        player.setDisplayName(coloring(prefix + player.getName()));


    }

    private String coloring(String message) {
        return breezeCoreManager.getMessageFormat().coloring(message);
    }
}
