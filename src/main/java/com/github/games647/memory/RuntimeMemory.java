package com.github.games647.memory;

public class RuntimeMemory {

    public static void getRuntimeMemory() {
        Runtime runtime = Runtime.getRuntime();
        long maxMemory = runtime.maxMemory();
        long freeMemory = runtime.freeMemory();
        long totalMemory = runtime.totalMemory();
    }
}
