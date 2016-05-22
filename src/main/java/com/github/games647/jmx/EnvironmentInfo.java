package com.github.games647.jmx;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;

public class EnvironmentInfo {

    public static void getOsInfo() {
        OperatingSystemMXBean osBean = ManagementFactory.getOperatingSystemMXBean();
        //os general info
        String osName = osBean.getName();
        String osVersion = osBean.getVersion();
        String osArch = osBean.getArch();

        //CPU
        int cores = osBean.getAvailableProcessors();
        String cpuName = System.getenv("PROCESSOR_IDENTIFIER");
        //only on *nix systems
        double loadAvg = osBean.getSystemLoadAverage();

        if (osBean instanceof com.sun.management.OperatingSystemMXBean) {
            getExtendedOsInfo((com.sun.management.OperatingSystemMXBean) osBean);
        }
    }

    //only available in oracle java
    public static void getExtendedOsInfo(com.sun.management.OperatingSystemMXBean sunOsBean) {
        //cpu
        double systemCpuLoad = sunOsBean.getSystemCpuLoad();
        double processCpuLoad = sunOsBean.getProcessCpuLoad();

        //swap
        long totalSwap = sunOsBean.getTotalSwapSpaceSize();
        long freeSwap = sunOsBean.getFreeSwapSpaceSize();

        //RAM
        long totalMemory = sunOsBean.getTotalPhysicalMemorySize();
        long freeMemory = sunOsBean.getFreePhysicalMemorySize();
    }

    public static void getDiskInfo() {
        File[] listRoots = File.listRoots();
        long totalSpace = 0;
        long freeSpace = 0;
        for (File rootFile : listRoots) {
            freeSpace += rootFile.getUsableSpace();
            totalSpace += rootFile.getTotalSpace();
        }
    }
}
