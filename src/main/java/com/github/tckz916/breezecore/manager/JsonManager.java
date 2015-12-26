package com.github.tckz916.breezecore.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.bukkit.entity.Player;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;

/**
 * Created by tckz916 on 2015/10/04.
 */
public class JsonManager {

    public String getJson(URL url) throws IOException {
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("User-agent", "Mozilla/5.0");
        BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));

        StringBuilder builder = new StringBuilder();

        String line = null;
        while ((line = reader.readLine()) != null) {
            builder.append(line);
        }
        reader.close();

        return builder.toString();
    }

    public String getJson(File file) throws IOException {
        FileReader fileReader = new FileReader(file);
        BufferedReader bufferedReader = new BufferedReader(fileReader);

        StringBuilder builder = new StringBuilder();

        String line = null;
        while ((line = bufferedReader.readLine()) != null) {
            builder.append(line);
        }
        bufferedReader.close();

        return builder.toString();
    }

    public String toJson(Object object) {
        Gson gson = new GsonBuilder().enableComplexMapKeySerialization().setPrettyPrinting().create();
        return gson.toJson(object);
    }

    public JsonObject createJsonObject(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, JsonObject.class);
    }

    public JsonArray createJsonArray(String json) {
        Gson gson = new Gson();
        return gson.fromJson(json, JsonArray.class);
    }

    public void writeJson(File file, String write) {
        try {
            FileWriter fileWriter = new FileWriter(file);
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            bufferedWriter.write(write);
            fileWriter.flush();
            bufferedWriter.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
    }

    public JsonObject playerJson(File file, Player player) {


        String json = null;
        try {
            json = getJson(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

        JsonObject root = createJsonObject(json);

        JsonObject playerdata;
        if (root != null) {
            playerdata = root.getAsJsonObject("Player");

            playerdata.addProperty("name", player.getName());
            playerdata.addProperty("uuid", player.getUniqueId().toString().replace("-", ""));
            playerdata.addProperty("full_uuid", player.getUniqueId().toString());
            playerdata.addProperty("language", player.spigot().getLocale());

            if (playerdata.get("prefix") == null) {
                playerdata.addProperty("prefix", "");
            }
            playerdata.addProperty("ip", player.spigot().getRawAddress().getAddress().getHostAddress());
            playerdata.addProperty("hostname", player.spigot().getRawAddress().getHostName());
            playerdata.addProperty("port", player.spigot().getRawAddress().getPort());
        } else {
            root = new JsonObject();
            playerdata = new JsonObject();

            playerdata.addProperty("name", player.getName());
            playerdata.addProperty("uuid", player.getUniqueId().toString().replace("-", ""));
            playerdata.addProperty("full_uuid", player.getUniqueId().toString());
            playerdata.addProperty("language", player.spigot().getLocale());

            playerdata.addProperty("prefix", "");

            playerdata.addProperty("ip", player.spigot().getRawAddress().getAddress().getHostAddress());
            playerdata.addProperty("hostname", player.spigot().getRawAddress().getHostName());
            playerdata.addProperty("port", player.spigot().getRawAddress().getPort());

            root.add("Player", playerdata);
        }

        return root;
    }


}
