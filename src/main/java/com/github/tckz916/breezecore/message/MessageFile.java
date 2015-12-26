package com.github.tckz916.breezecore.message;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by tckz916 on 2015/09/02.
 */
public class MessageFile {

    private static final Charset CONFIG_CHAREST = StandardCharsets.UTF_8;


    private FileConfiguration cfg = null;

    public MessageFile(String file) {
        //this.cfg = YamlConfiguration.loadConfiguration(this.getClass().getClassLoader().getResourceAsStream(file));
        try (Reader reader = new InputStreamReader(this.getClass().getClassLoader().getResourceAsStream(file), CONFIG_CHAREST)) {
            this.cfg = new YamlConfiguration();
            cfg.load(reader);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FileConfiguration getCfg() {
        return cfg;
    }

    public String get(String key) {
        return cfg.getString(key);
    }

}
