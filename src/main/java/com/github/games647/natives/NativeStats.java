package com.github.games647.natives;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.hyperic.sigar.CpuInfo;
import org.hyperic.sigar.CpuPerc;
import org.hyperic.sigar.FileSystemUsage;
import org.hyperic.sigar.Mem;
import org.hyperic.sigar.NetInterfaceStat;
import org.hyperic.sigar.Sigar;
import org.hyperic.sigar.SigarException;

public class NativeStats {

    public static void setSigarLibraryFolder(String folder) {
        //setting the location where sigar can find the library
        //otherwise it would lookup the library path of Java
        System.setProperty("org.hyperic.sigar.path", folder);
    }

    public static void getNativeInfo() {
        Sigar sigar = new Sigar();
        try {
            int uptime = (int) sigar.getUptime().getUptime();

            CpuInfo[] cpuInfoList = sigar.getCpuInfoList();
            int mhz = cpuInfoList[0].getMhz();

            CpuPerc cpuPerc = sigar.getCpuPerc();
            //IO wait
            double wait = cpuPerc.getWait();

            Mem mem = sigar.getMem();
            //included cache
            long actualUsed = mem.getActualUsed();
            long used = mem.getUsed();

            long cache = used - actualUsed;

            //net upload download
            NetInterfaceStat usedNetInterfaceStat = null;
            String[] netInterfaceList = sigar.getNetInterfaceList();
            for (String interfaceName : netInterfaceList) {
                NetInterfaceStat interfaceStat = sigar.getNetInterfaceStat(interfaceName);
                if (interfaceStat.getRxBytes() != 0) {
                    usedNetInterfaceStat = interfaceStat;
                    break;
                }
            }

            if (usedNetInterfaceStat != null) {
                long speed = usedNetInterfaceStat.getSpeed();

                long receivedBytes = usedNetInterfaceStat.getRxBytes();
                long sentBytes = usedNetInterfaceStat.getTxBytes();
            }

            String rootFileSystem = File.listRoots()[0].getAbsolutePath();
            FileSystemUsage fileSystemUsage = sigar.getFileSystemUsage(rootFileSystem);
            long diskReadBytes = fileSystemUsage.getDiskReadBytes();
            long diskWriteBytes = fileSystemUsage.getDiskWriteBytes();
        } catch (SigarException ex) {
            Logger.getLogger(NativeStats.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            sigar.close();
        }
    }
}
