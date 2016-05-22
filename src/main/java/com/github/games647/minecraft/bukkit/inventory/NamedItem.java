package com.github.games647.minecraft.bukkit.inventory;

import java.util.Arrays;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class NamedItem {

    public static void give(Player toPlayer, Material material, String name, String[] lore) {
        ItemStack newItem = new ItemStack(material);

        ItemMeta meta = newItem.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        
        newItem.setItemMeta(meta);
        toPlayer.getInventory().addItem(new ItemStack[]{newItem});
    }
}
