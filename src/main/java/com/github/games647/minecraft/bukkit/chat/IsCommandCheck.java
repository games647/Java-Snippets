package com.github.games647.minecraft.bukkit.chat;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;

public class IsCommandCheck {

    public boolean isCommand(String command) {
        PluginManager manager = Bukkit.getServer().getPluginManager();
        SimplePluginManager spmanager = (SimplePluginManager) manager;
        SimpleCommandMap commandMap = null;
        try {
           Field commandMapField = spmanager.getClass().getDeclaredField("commandMap");
           commandMapField.setAccessible(true);
           commandMap = (SimpleCommandMap) commandMapField.get(spmanager);
        } catch (NoSuchFieldException | IllegalAccessException exception) {
            //WARNING: HANDLE THIS EXCEPTION
        }

        return commandMap.getCommand(command) != null;
    }
}
