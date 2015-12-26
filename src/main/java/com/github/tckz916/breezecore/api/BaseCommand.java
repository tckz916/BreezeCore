package com.github.tckz916.breezecore.api;

import com.github.tckz916.breezecore.BreezeCore;
import com.github.tckz916.breezecore.BreezeCoreManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.command.RemoteConsoleCommandSender;
import org.bukkit.entity.Player;

/**
 * Created by tckz916 on 2015/09/03.
 */
public abstract class BaseCommand {

    private BreezeCoreManager breezeCoreManager = BreezeCoreManager.getInstance();

    private final CommandSender sender;

    private final String permission;

    private final String description;

    private final String usage;

    public BaseCommand(CommandSender sender, String permission, String description, String usage) {
        this.sender = sender;
        this.permission = permission;
        this.description = description;
        this.usage = usage;
    }

    public CommandSender getSender() {
        return sender;
    }

    public String getPermmison() {
        return permission;
    }

    public String getDescription() {
        return description;
    }

    public String getUsage() {
        return usage;
    }

    public boolean hasPermission() {
        return sender.hasPermission(permission);
    }

    public void sendUsage() {
        sender.sendMessage(format(false, "error.usage.permission", permission));
        sender.sendMessage(format(false, "error.usage.description", description));
        sender.sendMessage(format(false, "error.usage.usage", usage));
    }

    public boolean isSenderPlayer() {
        return (sender instanceof Player);
    }

    public boolean isSenderConsole() {
        return (sender instanceof ConsoleCommandSender);
    }

    public boolean isSenderRemoteConsole() {
        return (sender instanceof RemoteConsoleCommandSender);
    }

    public abstract void execute(CommandSender sender, Command command, String label, String[] args);

    private String format(boolean prefix, String key, Object... args) {
        return breezeCoreManager.getMessageFormat().format(prefix, key, args);
    }
}
