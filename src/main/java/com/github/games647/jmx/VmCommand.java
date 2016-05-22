package com.github.games647.jmx;

import java.lang.management.ClassLoadingMXBean;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.util.List;

public class VmCommand {

    public static void getJavaInfo() {
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        String vmName = runtimeBean.getVmName();
        String javaVersion = System.getProperty("java.version");
        String javaVendor = runtimeBean.getVmVendor() + ' ' + runtimeBean.getVmVersion();
    }

    public static void getSpecInfo() {
        RuntimeMXBean runtimeBean = ManagementFactory.getRuntimeMXBean();
        String specName = runtimeBean.getSpecName();
        String specVendor = runtimeBean.getSpecVendor();
        String specVersion = runtimeBean.getSpecVersion();
    }

    public static void getClassInfo() {
        ClassLoadingMXBean classBean = ManagementFactory.getClassLoadingMXBean();
        long loadedClasses = classBean.getLoadedClassCount();
        long totalLoadedClasses = classBean.getTotalLoadedClassCount();
        long unloadedClasses = classBean.getUnloadedClassCount();
    }

    public static void getGarbageCollectorInfo() {
        List<GarbageCollectorMXBean> gcBean = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean collector : gcBean) {
            String collectorName = collector.getName();
            long collectionTime = collector.getCollectionTime();
            long collectionCount = collector.getCollectionCount();
        }
    }
}
