package com.github.games647;

public class StringSnippets {

    public static String concate(String... arguments) {
        StringBuilder sb = new StringBuilder();
        for (String argument : arguments) {
            sb.append(argument).append(' ');
        }

        String allArgs = sb.toString().trim();
        return allArgs;
    }
}
