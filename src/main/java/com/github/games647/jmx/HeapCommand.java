package com.github.games647.jmx;

import java.io.File;
import java.lang.management.ManagementFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.management.InstanceNotFoundException;
import javax.management.MBeanException;
import javax.management.MBeanServer;
import javax.management.MalformedObjectNameException;
import javax.management.ObjectName;
import javax.management.ReflectionException;

public class HeapCommand {

    //https://docs.oracle.com/javase/8/docs/jre/api/management/extension/com/sun/management/DiagnosticCommandMBean.html
    private static final String DIAGNOSTIC_COMMAND = "com.sun.management:type=DiagnosticCommand";
    private static final String HEAP_COMMAND = "gcClassHistogram";

    //can be useful for dumping heaps in binary format
    //https://docs.oracle.com/javase/8/docs/jre/api/management/extension/com/sun/management/HotSpotDiagnosticMXBean.html
    private static final String HOTSPOT_DIAGNOSTIC = "com.sun.management:type=HotSpotDiagnostic";
    private static final String DUMP_COMMAND = "dumpHeap";
    private static final String DUMP_FILE_NAME = "heap";
    private static final String DUMP_FILE_ENDING = ".hprof";
    private static final boolean DUMP_DEAD_OBJECTS = false;

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    public static void dumpHeap(String dumpFolder)
            throws MalformedObjectNameException, InstanceNotFoundException, MBeanException, ReflectionException {
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        ObjectName hotspotBean = ObjectName.getInstance(HOTSPOT_DIAGNOSTIC);

        String timeSuffix = '-' + dateFormat.format(new Date());
        File dumpFile = new File(dumpFolder, DUMP_FILE_NAME + timeSuffix + DUMP_FILE_ENDING);
        //it needs to be with a system dependent path seperator
        mBeanServer.invoke(hotspotBean, DUMP_COMMAND
                , new Object[]{dumpFile.getAbsolutePath(), DUMP_DEAD_OBJECTS}
                , new String[]{String.class.getName(), Boolean.TYPE.getName()});
    }

    public static void getHeap()
            throws MalformedObjectNameException, InstanceNotFoundException, MBeanException, ReflectionException {
                    ObjectName diagnosticObjectName = ObjectName.getInstance(DIAGNOSTIC_COMMAND);

        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        String reply = (String) mBeanServer.invoke(diagnosticObjectName, HEAP_COMMAND
                , new Object[]{new String[]{}}, new String[]{String[].class.getName()});
        System.out.println(reply);
    }
}
