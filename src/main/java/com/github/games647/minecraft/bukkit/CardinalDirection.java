package com.github.games647.minecraft.bukkit;

import org.bukkit.entity.Player;

public class CardinalDirection {

    public enum Direction {
        NW,
        N,
        NE,
        E,
        SE,
        S,
        SW,
        W
    }

    public Direction getCardinalDirection(Player player)  {
        double rotation = (player.getLocation().getYaw() - 90) % 360;
        if (rotation < 0) {
            rotation += 360.0;
        }

        if (0 <= rotation && rotation < 67.5) {
            return Direction.NW; //NorthWest
        } else if (67.5 <= rotation && rotation < 112.5) {
            return Direction.N; //North
        } else if (112.5 <= rotation && rotation < 157.5) {
            return Direction.NE; //NorthEeast
        } else if (157.5 <= rotation && rotation < 202.5) {
            return Direction.E; //East
        } else if (202.5 <= rotation && rotation < 247.5) {
            return Direction.SE; //SouthEast
        } else if (247.5 <= rotation && rotation < 292.5) {
            return Direction.S; //South
        } else if (292.5 <= rotation && rotation < 337.5) {
            return Direction.SW; //SouthWest
        } else if (337.5 <= rotation && rotation < 360.0) {
            return Direction.W; //West
        } else {
            return null;
        }
    }
}
