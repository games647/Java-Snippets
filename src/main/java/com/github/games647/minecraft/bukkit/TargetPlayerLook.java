package com.github.games647.minecraft.bukkit;

import org.apache.commons.lang.Validate;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

/**
 * Source: https://www.spigotmc.org/threads/code-snippet-helpful-snippets-for-bukkit-plugins.35124/
 */
public class TargetPlayerLook {

    /**
     * Iterates through all players and checks for the nearest focused player, (eye location, middle location and feet
     * location of every targeted player for maximum precision)
     *
     * @param player the player to check the target of
     * @param maxRange the maximum range; higher -> take higher precision; lower -> take lower precision (maybe)
     * @param precision sth. between 0.97D and 0.999D; higher -> more precise checking; lower -> less precise // TODO
     * test precision
     * @return the nearest player the given player can see and is focused or null, if no player matches the conditions
     */
    public static Player getTargetedPlayerOfPlayer(Player player, double maxRange, double precision) {
        Validate.notNull(player, "player may not be null");
        Validate.isTrue(maxRange > 0D, "the maximum range has to be greater than 0");
        Validate.isTrue(precision > 0D && precision < 1D, "the precision has to be greater than 0 and smaller than 1");
        double maxRange2 = maxRange * maxRange;

        String playerName = player.getName();
        Location fromLocation = player.getEyeLocation();
        String playersWorldName = fromLocation.getWorld().getName();
        Vector playerDirection = fromLocation.getDirection().normalize();
        Vector playerVectorPos = fromLocation.toVector();

        Player target = null;
        double targetDistance2 = Double.MAX_VALUE;
        // check with target's feed location
        for (Player somePlayer : Bukkit.getServer().getOnlinePlayers()) {
            if (somePlayer.getName().equals(playerName)) {
                continue;
            }

            Location somePlayerLocation_middle = getMiddleLocationOfPlayer(somePlayer);
            Location somePlayerLocation_eye = somePlayer.getEyeLocation();
            Location somePlayerLocation_feed = somePlayer.getLocation();
            if (!somePlayerLocation_eye.getWorld().getName().equals(playersWorldName)) {
                continue;
            }

            double newTargetDistance2_middle = somePlayerLocation_middle.distanceSquared(fromLocation);
            double newTargetDistance2_eye = somePlayerLocation_eye.distanceSquared(fromLocation);
            double newTargetDistance2_feed = somePlayerLocation_feed.distanceSquared(fromLocation);

            // check angle to target:
            // middle location
            if (newTargetDistance2_middle <= maxRange2) {
                Vector toTarget_middle = somePlayerLocation_middle.toVector().subtract(playerVectorPos).normalize();
                // check the dotProduct instead of the angle, because it's faster:
                double dotProduct_middle = toTarget_middle.dot(playerDirection);
                if (dotProduct_middle > precision && player.hasLineOfSight(somePlayer)
                        && (target == null || newTargetDistance2_middle < targetDistance2)) {
                    target = somePlayer;
                    targetDistance2 = newTargetDistance2_middle;
                    continue;
                }
            }
            // eye location
            if (newTargetDistance2_eye <= maxRange2) {
                final Vector toTarget_eye = somePlayerLocation_eye.toVector().subtract(playerVectorPos).normalize();
                final double dotProduct_eye = toTarget_eye.dot(playerDirection);
                if (dotProduct_eye > precision && player.hasLineOfSight(somePlayer)
                        && (target == null || newTargetDistance2_eye < targetDistance2)) {
                    target = somePlayer;
                    targetDistance2 = newTargetDistance2_eye;
                    continue;
                }
            }
            // feed location
            if (newTargetDistance2_feed <= maxRange2) {
                Vector toTarget_feed = somePlayerLocation_feed.toVector().subtract(playerVectorPos).normalize();
                double dotProduct_feed = toTarget_feed.dot(playerDirection);
                if (dotProduct_feed > precision && player.hasLineOfSight(somePlayer)
                        && (target == null || newTargetDistance2_feed < targetDistance2)) {
                    target = somePlayer;
                    targetDistance2 = newTargetDistance2_feed;
                }
            }
        }
        
        return target;
    }

    public static Location getMiddleLocationOfPlayer(Player player) {
        return player.getLocation().add(0, player.getEyeHeight() / 2, 0);
    }

}
