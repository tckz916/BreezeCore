package com.github.tckz916.breezecore.command;

import com.github.tckz916.breezecore.BreezeCoreManager;
import com.github.tckz916.breezecore.api.BaseCommand;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.SkullMeta;

public class MobHeadCommand extends BaseCommand {

    private BreezeCoreManager breezeCoreManager = BreezeCoreManager.getInstance();
    public static final String PERMISSION = "breezecore.command.mobhead";
    public static final String DESCRIPTION = "Item Command";
    public static final String USAGE = "\n/mobhead\n/mobhead <player>";

    public MobHeadCommand(CommandSender sender) {
        super(sender, "breezecore.command.mobhead", "Item Command", "\n/mobhead\n/mobhead <player>");
    }

    public void execute(CommandSender sender, Command command, String label, String[] args) {
        if (!this.hasPermission()) {
            sender.sendMessage(this.format(false, "error.no-permission", new Object[0]));
        } else if (this.isSenderConsole()) {
            sender.sendMessage(this.format(false, "error.console", new Object[0]));
        } else if (this.isSenderRemoteConsole()) {
            sender.sendMessage(this.format(false, "error.console", new Object[0]));
        } else if (args.length > 2) {
            this.sendUsage();
        } else {
            Player player = (Player) sender;

            switch (args.length) {
            case 0:
                this.getSkull(player, player.getName());
                break;

            case 1:
                if (!args[0].matches("[A-Za-z0-9_]{2,16}")) {
                    sender.sendMessage(this.format(false, "error.player-not-found", new Object[0]));
                    return;
                }

                this.getSkull(player, args[0]);
            }

        }
    }

    private void getSkull(Player player, String name) {
        PlayerInventory inventory = player.getInventory();
        ItemStack itemStack = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
        SkullMeta skullmeta = (SkullMeta) itemStack.getItemMeta();

        skullmeta.setOwner(name);
        itemStack.setItemMeta(skullmeta);
        inventory.addItem(new ItemStack[] { itemStack});
        String message = this.format(true, "message.mobhead", new Object[0]).replace("%player%", name);

        player.sendMessage(message);
    }

    private String format(boolean prefix, String key, Object... args) {
        return this.breezeCoreManager.getMessageFormat().format(prefix, key, args);
    }
}
