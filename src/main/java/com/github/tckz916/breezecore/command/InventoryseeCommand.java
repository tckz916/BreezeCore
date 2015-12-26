package com.github.tckz916.breezecore.command;

import com.github.tckz916.breezecore.BreezeCore;
import com.github.tckz916.breezecore.BreezeCoreManager;
import com.github.tckz916.breezecore.api.BaseCommand;
import com.github.tckz916.breezecore.manager.FileManager;
import com.github.tckz916.breezecore.manager.JsonManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class InventoryseeCommand extends BaseCommand {

    private BreezeCore plugin = BreezeCore.getInstance();
    private BreezeCoreManager breezeCoreManager = BreezeCoreManager.getInstance();
    private FileManager fileManager;
    private JsonManager jsonManager;
    public static final String PERMISSION = "breezecore.command.uuid";
    public static final String DESCRIPTION = "Uuid Command";
    public static final String USAGE = "/Uuid <player>";

    public InventoryseeCommand(CommandSender sender) {
        super(sender, "breezecore.command.uuid", "Uuid Command", "/Uuid <player>");
        this.fileManager = this.breezeCoreManager.getFileManager();
        this.jsonManager = this.breezeCoreManager.getJsonManager();
    }

    public void execute(CommandSender sender, Command command, String label, String[] args) {
        if (!this.hasPermission()) {
            sender.sendMessage(this.format(false, "error.no-permission", new Object[0]));
        } else if (this.isSenderConsole()) {
            sender.sendMessage(this.format(false, "error.console", new Object[0]));
        } else if (this.isSenderRemoteConsole()) {
            sender.sendMessage(this.format(false, "error.console", new Object[0]));
        } else if (args.length >= 1 && args.length <= 1) {
            Player player = (Player) sender;
            Player target = Bukkit.getPlayer(args[0]);

            player.openInventory(target.getInventory());
        } else {
            this.sendUsage();
        }
    }

    private String coloring(String message) {
        return this.breezeCoreManager.getMessageFormat().coloring(message);
    }

    private String format(boolean prefix, String key, Object... args) {
        return this.breezeCoreManager.getMessageFormat().format(prefix, key, args);
    }
}
