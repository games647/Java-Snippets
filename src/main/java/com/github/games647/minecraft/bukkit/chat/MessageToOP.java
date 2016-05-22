package com.github.games647.minecraft.bukkit.chat;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageToOP {

    public static void sendMessageToOperators(String message, CommandSender exclude) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            if (player.isOp()) {
                if (!exclude.getName().equals(player.getName())) {
                    player.sendMessage(message);
                }
            }
        }
    }
}
