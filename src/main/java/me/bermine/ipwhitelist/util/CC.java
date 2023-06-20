package me.bermine.ipwhitelist.util;

import com.google.common.base.Preconditions;
import org.bukkit.ChatColor;

public class CC {

    public static String translate(String s) {
        Preconditions.checkNotNull(s, "s cannot be null");
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
