package com.github.tckz916.breezecore;

import com.github.tckz916.breezecore.command.tabcompete.PrefixTabComple;
import com.github.tckz916.breezecore.command.tabcompete.RotationTabComplete;
import com.github.tckz916.breezecore.listener.PlayerListener;
import org.bukkit.command.TabCompleter;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.ScoreboardManager;

import java.util.logging.Logger;

/**
 * Created by tckz916 on 2015/10/05.
 */
public class BreezeCore extends JavaPlugin {

    private static BreezeCore instance = null;

    private PluginManager pluginManager = this.getServer().getPluginManager();


    @Override
    public void onEnable() {
        super.onEnable();

        instance = this;

        new BreezeCoreManager();


        registercommand("breezecore");
        registercommand("stats");
        registercommand("recentstats");
        registercommand("uuid");
        registercommand("rotation");
        registercommand("prefix");

        registerlistener(new PlayerListener());

        registertabcomplete("rotation", new RotationTabComplete());
        registertabcomplete("prefix",new PrefixTabComple());

        saveDefaultConfig();
    }



    @Override
    public void onDisable() {
        super.onDisable();
    }

    private void registercommand(String cmd) {
        getCommand(cmd).setExecutor(new BreezeCoreCommandHandler());
    }

    private void registerlistener(Listener listener) {
        pluginManager.registerEvents(listener, this);
    }

    private void registertabcomplete(String cmd, TabCompleter completer) {
        getCommand(cmd).setTabCompleter(completer);
    }

    public static BreezeCore getInstance() {
        return instance;
    }
}
