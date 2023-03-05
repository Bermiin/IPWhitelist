package me.bermine.ipwhitelist;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class IPWhitelist extends JavaPlugin implements Listener {

    @Override
    public void onEnable() {
        File file = new File(this.getDataFolder(), "config.yml");
        if (!file.exists()) {
            this.getConfig().options().copyDefaults(true);
            this.saveDefaultConfig();
        }
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("ipw").setExecutor(this);
        getLogger().info("Plugin enabled!");
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("ipw")) {
            if (args.length == 1 && args[0].equals("reload")) {
                this.reloadConfig();
                sender.sendMessage(colored(getConfig().getString("config_reloaded")));
            } else {
                sender.sendMessage(ChatColor.RED + "Usage: /ipw reload");
            }
        }
        return false;
    }

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent e) {
		getLogger().info("Incoming connection from " + e.getAddress().toString());
        for (String ip : getConfig().getStringList("whitelisted_ips")) {
            if (!ip.equals(e.getAddress().toString().replace("/", ""))) {
                e.setKickMessage(colored(getConfig().getString("kick_message")));
                e.setLoginResult(Result.KICK_OTHER);
				getLogger().info("Incoming connection from " + e.getAddress().toString() + " was blocked.");
            }
        }
    }

    private String colored(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
