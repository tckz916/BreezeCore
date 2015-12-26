package com.github.tckz916.breezecore.command;

import com.github.tckz916.breezecore.BreezeCore;
import com.github.tckz916.breezecore.BreezeCoreManager;
import com.github.tckz916.breezecore.api.BaseCommand;
import com.sun.management.OperatingSystemMXBean;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.management.ManagementFactory;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ServerInfoCommand extends BaseCommand {

    private BreezeCore plugin = BreezeCore.getInstance();
    private BreezeCoreManager breezeCoreManager = BreezeCoreManager.getInstance();
    public static final String PERMISSION = "breezecore.command.serverinfo";
    public static final String DESCRIPTION = "ServerInfo Command";
    public static final String USAGE = "/serverinfo";

    public ServerInfoCommand(CommandSender sender) {
        super(sender, "breezecore.command.serverinfo", "ServerInfo Command", "/serverinfo");
    }

    public void execute(CommandSender sender, Command command, String label, String[] args) {
        if (!this.hasPermission()) {
            sender.sendMessage(this.format(false, "error.no-permission", new Object[0]));
        } else if (args.length > 0) {
            this.sendUsage();
        } else {
            String ip = null;

            try {
                ip = this.getIp();
            } catch (IOException ioexception) {
                ioexception.printStackTrace();
            }

            OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();
            Player player = (Player) sender;
            ArrayList entities = new ArrayList(player.getWorld().getEntities());
            double scl = osmxb.getSystemCpuLoad() * 100.0D;
            BigDecimal scl1 = new BigDecimal(scl);
            BigDecimal scl2 = scl1.setScale(1, 4);

            sender.sendMessage(this.coloring("$7CPU Usage: $b" + scl2.doubleValue() + "$r%"));
            sender.sendMessage(this.coloring("$7Global IP: $b" + ip));
            sender.sendMessage(this.coloring("$7Port: $b" + this.plugin.getServer().getPort()));
            sender.sendMessage(this.coloring("$7Current World: $b" + player.getWorld().getName()));
            sender.sendMessage(this.coloring("$7Entity Total: $b" + entities.size()));
            sender.sendMessage(this.coloring("$7Online Player: $b" + this.plugin.getServer().getOnlinePlayers().size()));
            sender.sendMessage(this.coloring("$7Max Player: $b" + this.plugin.getServer().getMaxPlayers()));
            sender.sendMessage(this.coloring("$7Server Version: $b" + this.plugin.getServer().getVersion()));
            sender.sendMessage(this.coloring("$7Bukkit Version: $b" + this.plugin.getServer().getBukkitVersion()));
        }
    }

    private String getIp() throws IOException {
        URL url = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;

        String s;

        try {
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            String ip = in.readLine();

            s = ip;
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException ioexception) {
                    ioexception.printStackTrace();
                }
            }

        }

        return s;
    }

    private String format(boolean prefix, String key, Object... args) {
        return this.breezeCoreManager.getMessageFormat().format(prefix, key, args);
    }

    private String coloring(String msg) {
        return this.breezeCoreManager.getMessageFormat().coloring(msg);
    }
}
