package com.github.games647.memory;

import java.lang.instrument.Instrumentation;

/**
 * Description: ...
 *
 * Source: http://www.javamex.com/tutorials/memory/object_memory_usage.shtml
 */
public class ObjectHeapSize {

    private static Instrumentation instrumentation;

    public static void premain(String args, Instrumentation inst) {
        instrumentation = inst;
    }

    public static long getObjectSize(Object o) {
        return instrumentation.getObjectSize(o);
    }

    public static void main(String[] args) {
        String test = "hallo world";
        //this includes the overhead and references (4 bytes) 
        //but it does not include the size of references object in fields
        System.out.println(getObjectSize(test));
    }
}
