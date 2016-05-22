package com.github.games647.minecraft.bukkit;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 *
 * Source: https://gist.github.com/codebucketdev/7711362
 */
public class PullPlayer {

    public static void pullPlayer(Player player, Location location) {
        Location playerLoc = player.getLocation();

        playerLoc.setY(playerLoc.getY() + 0.5D);
        player.teleport(playerLoc);

        double g = -0.08D;
        double d = location.distance(playerLoc);
        double t = d;
        double v_x = (1.0D + 0.07000000000000001D * t) * (location.getX() - playerLoc.getX()) / t;
        double v_y = (1.0D + 0.03D * t) * (location.getY() - playerLoc.getY()) / t - 0.5D * g * t;
        double v_z = (1.0D + 0.07000000000000001D * t) * (location.getZ() - playerLoc.getZ()) / t;

        Vector v = player.getVelocity();
        v.setX(v_x);
        v.setY(v_y);
        v.setZ(v_z);
        player.setVelocity(v);
    }
}
