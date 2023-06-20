package me.bermine.ipwhitelist;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
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
        getCommand("ipw").setExecutor(new IPWhitelistCommand(this));
        getLogger().info("Plugin enabled!");
    }

    @EventHandler
    public void onPreLogin(AsyncPlayerPreLoginEvent e) {
        if (!getConfig().getStringList("whitelisted_ips").contains(e.getAddress().getHostAddress())) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, colored(getConfig().getString("kick_message")));
        }
    }

    private String colored(String message) {
        return ChatColor.translateAlternateColorCodes('&', message);
    }
}
