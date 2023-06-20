package me.bermine.ipwhitelist;

import me.bermine.ipwhitelist.util.CC;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

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
        List<String> list = getConfig().getStringList("whitelisted_ips");
        if (getConfig().getString("policy").equalsIgnoreCase("WHITELIST") && !list.contains(e.getAddress().getHostAddress())) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_WHITELIST, CC.translate(getConfig().getString("kick_message")));
            return;
        }
        if (getConfig().getString("policy").equalsIgnoreCase("BLACKLIST") && list.contains(e.getAddress().getHostAddress())) {
            e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, CC.translate(getConfig().getString("kick_message")));
        }
    }
}
