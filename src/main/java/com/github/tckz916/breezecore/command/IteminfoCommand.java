package com.github.tckz916.breezecore.command;

import com.github.tckz916.breezecore.BreezeCoreManager;
import com.github.tckz916.breezecore.api.BaseCommand;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class IteminfoCommand extends BaseCommand {

    private BreezeCoreManager breezeCoreManager = BreezeCoreManager.getInstance();
    public static final String PERMISSION = "breezecore.command.iteminfo";
    public static final String DESCRIPTION = "Item Command";
    public static final String USAGE = "/iteminfo";

    public IteminfoCommand(CommandSender sender) {
        super(sender, "breezecore.command.iteminfo", "Item Command", "/iteminfo");
    }

    public void execute(CommandSender sender, Command command, String label, String[] args) {
        if (!this.hasPermission()) {
            sender.sendMessage(this.format(false, "error.no-permission", new Object[0]));
        } else if (this.isSenderConsole()) {
            sender.sendMessage(this.format(false, "error.console", new Object[0]));
        } else if (this.isSenderRemoteConsole()) {
            sender.sendMessage(this.format(false, "error.console", new Object[0]));
        } else if (args.length > 0) {
            this.sendUsage();
        } else {
            Player player = (Player) sender;
            ItemStack hand = player.getItemInHand();
            String id = String.valueOf(hand.getType().getId());
            String damage = String.valueOf(hand.getDurability());

            sender.sendMessage(this.format(true, "message.iteminfo", new Object[0]).replace("%material%", hand.getType().name()).replace("%id%", id).replace("%damage%", damage));
        }
    }

    private String format(boolean prefix, String key, Object... args) {
        return this.breezeCoreManager.getMessageFormat().format(prefix, key, args);
    }
}
