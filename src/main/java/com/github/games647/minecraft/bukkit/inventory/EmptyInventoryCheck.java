package com.github.games647.minecraft.bukkit.inventory;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class EmptyInventoryCheck {


    public static boolean isIventoryEmpty(PlayerInventory playerInventory) {
        if (isIventoryEmpty((Inventory) playerInventory)) {
            for (ItemStack item : playerInventory.getArmorContents()) {
                if (item != null && item.getType() != Material.AIR) {
                    return false;
                }
            }
        }

        return true;
    }

    public static boolean isIventoryEmpty(Inventory inventory) {
        for (ItemStack item : inventory.getContents()) {
            if (item != null && item.getType() != Material.AIR) {
                return false;
            }
        }

        return true;
    }

    public static boolean isIventoryEmpty(Player player) {
        return isIventoryEmpty(player.getInventory());
    }
}
