package com.github.tckz916.breezecore.message;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by tckz916 on 2015/09/02.
 */
public class MessageFormat {

    private final MessageFile messageFile;


    public MessageFormat() {
        this.messageFile = new MessageFile("messages.yml");
    }

    public String format(String key, Object... args) {
        return format(true, key, args);
    }

    public String format(boolean prefix, String key, Object... args) {
        String message = prefix ? messageFile.get("prefix") + messageFile.get(key) : messageFile.get(key);
        for (int i = 0; i < args.length; i++) {
            message = message.replace("{" + i + "}", String.valueOf(args[i]));
        }
        return coloring(message);
    }

    public String prefix(String msg) {
        return coloring(messageFile.get("prefix") + msg);
    }

    public MessageFile getMessageFile() {
        return messageFile;
    }

    public String coloring(String str) {
        return ChatColor.translateAlternateColorCodes('$', str);
    }

    public String build(String[] strings, int start) {
        if (strings.length >= start + 1) {
            String str = strings[start];
            if (strings.length >= start + 2) {
                for (int i = start + 1; i < strings.length; i++) {
                    str = str + " " + strings[i];
                }
            }
            return str;
        } else {
            return null;
        }
    }

    public void broadcastMessage(String msg) {
        for (Player player : Bukkit.getServer().getOnlinePlayers()) {
            player.sendMessage(msg);
        }
    }
}
