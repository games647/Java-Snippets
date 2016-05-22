package com.github.games647.minecraft.bukkit;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

public class ClosestPlayer {

    public static Player getClosestPlayer(Entity entity) {
        Location sourceLocation = entity.getLocation();

        double minDistance = Double.MAX_VALUE;
        Player closestPlayer = null;
        for (Player other : Bukkit.getOnlinePlayers()) {
            double difference = other.getLocation().distance(sourceLocation);
            if (difference < minDistance) {
                minDistance = difference;
                closestPlayer = other;
            }
        }

        return closestPlayer;
    }
}
