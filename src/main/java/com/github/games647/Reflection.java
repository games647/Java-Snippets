package com.github.games647;

import java.lang.reflect.Field;

public class Reflection {

    public static void setPrivateField(Object targetObject, String name, Object value) {
        try {
            Field field = targetObject.getClass().getDeclaredField(name);
            field.setAccessible(true);
            field.set(targetObject, value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
