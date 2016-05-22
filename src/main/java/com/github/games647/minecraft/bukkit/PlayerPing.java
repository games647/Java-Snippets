package com.github.games647.minecraft.bukkit;

import com.google.common.collect.Lists;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class PlayerPing {

    private static Method getHandleMethod;
    private static Field pingField;

    public static int getReflectionPing(Player player) {
        try {
            if (getHandleMethod == null) {
                getHandleMethod = player.getClass().getDeclaredMethod("getHandle");
                //disable java security check. This will speed it a little
                getHandleMethod.setAccessible(true);
            }

            Object entityPlayer = getHandleMethod.invoke(player);

            if (pingField == null) {
                if (isModdedServer()) {
                    //MCPC has a remapper, but it doesn't work if we get the class dynamic
                    initMCPCPing(entityPlayer);
                } else {
                    pingField = entityPlayer.getClass().getDeclaredField("ping");
                    //disable java security check. This will speed it a little
                    pingField.setAccessible(true);
                }
            }

            //returns the found int value
            return pingField.getInt(entityPlayer);
        } catch (IllegalAccessException | NoSuchFieldException | NoSuchMethodException | InvocationTargetException ex) {
            //Forward the exception to replaceManager
            throw new RuntimeException(ex);
        }
    }

    private static boolean isModdedServer() {
        //aggressive checking for modded servers
        List<String> checkVersions = Lists.newArrayList(Bukkit.getVersion(), Bukkit.getName()
                , Bukkit.getServer().toString());

        for (String version : checkVersions) {
            if (version.contains("MCPC") || version.contains("Cauldron")) {
                return true;
            }
        }

        return false;
    }

    private static void initMCPCPing(Object entityPlayer) {
        //this isn't a secure, because it detects the ping variable by the ordering
        //a remaping (deobfuscate the variables) would work, but it won't be forwardcompatible
        Class<?> lastType = null;
        Field lastIntField = null;
        for (Field field : entityPlayer.getClass().getDeclaredFields()) {
            if (field.getType() == Integer.TYPE
                    && Modifier.isPublic(field.getModifiers())
                    && lastType == Boolean.TYPE) {
                lastIntField = field;
                continue;
            }

            if (field.getType() == Boolean.TYPE && lastIntField != null) {
                pingField = lastIntField;
                //disable java security check. This will speed it a little
                pingField.setAccessible(true);
                break;
            }

            lastIntField = null;
            lastType = field.getType();
        }
    }
}
