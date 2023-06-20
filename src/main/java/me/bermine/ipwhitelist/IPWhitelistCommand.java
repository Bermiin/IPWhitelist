package me.bermine.ipwhitelist;

import me.bermine.ipwhitelist.util.CC;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class IPWhitelistCommand implements CommandExecutor {

    private final IPWhitelist plugin;
    public IPWhitelistCommand(IPWhitelist plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!sender.hasPermission("ipwhitelist.reload")) {
            sender.sendMessage(CC.translate(plugin.getConfig().getString("no_permission")));
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(ChatColor.RED + "&cUsage: /ipw reload");
            return true;
        }
        if (args[0].equalsIgnoreCase("reload")) {
            plugin.reloadConfig();
            sender.sendMessage(CC.translate(plugin.getConfig().getString("config_reloaded")));
            return false;
        }
        return false;
    }
}
