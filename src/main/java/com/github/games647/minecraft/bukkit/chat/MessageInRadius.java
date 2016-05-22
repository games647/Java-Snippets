package com.github.games647.minecraft.bukkit.chat;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class MessageInRadius {

    public static void sendMessageRadius(double radius, Location loc, String message) {

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getWorld().equals(loc.getWorld()) && p.getLocation().distance(loc) <= radius) {
                p.sendMessage(message);
            }
        }
    }
}
