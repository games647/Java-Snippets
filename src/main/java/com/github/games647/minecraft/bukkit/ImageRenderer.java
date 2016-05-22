package com.github.games647.minecraft.bukkit;

import java.awt.image.BufferedImage;

import org.bukkit.entity.Player;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;

public class ImageRenderer extends MapRenderer {

    private BufferedImage image;

    public ImageRenderer(BufferedImage image) {
        super();

        this.image = image;
    }

    @Override
    public void render(MapView map, MapCanvas canvas, Player player) {
        //the image is just for the player who requested a new key
        if (image != null) {
            canvas.drawImage(0, 0, image);
            //release ressources in order to prevent memory leaks
            image = null;
        }
    }
}
