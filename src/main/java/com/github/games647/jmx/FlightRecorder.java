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

public class FlightRecorder {

    private static final String DIAGNOSTIC_COMMAND = "com.sun.management:type=DiagnosticCommand";
    private static final String START_COMMAND = "jfrStart";
    private static final String STOP_COMMAND = "jfrStop";
    private static final String DUMP_COMMAND = "jfrDump";

    private static final String UNLOCK_COMMERCIAL_COMMAND = "vmCheckCommercialFeatures";

    private static final String SETTINGS_FILE = "default.jfc";

    private static final String DUMP_FILE_NAME = "flight_recorder";
    private static final String DUMP_FILE_ENDING = ".jfr";

    private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd-HH-mm-ss");

    public static void startRecording(String settingsPath, String recordingsName)
            throws MalformedObjectNameException, InstanceNotFoundException, MBeanException, ReflectionException {
        ObjectName diagnosticObjectName = ObjectName.getInstance(DIAGNOSTIC_COMMAND);

        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        String reply = (String) mBeanServer.invoke(diagnosticObjectName, START_COMMAND
                , new Object[]{new String[]{"settings=" + settingsPath, "name=" + recordingsName}}
                , new String[]{String[].class.getName()});

        System.out.println(reply);
    }

    public static void stopRecording(String recordingsName)
            throws MalformedObjectNameException, InstanceNotFoundException, MBeanException, ReflectionException {
        ObjectName diagnosticObjectName = ObjectName.getInstance(DIAGNOSTIC_COMMAND);

        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();
        String reply = (String) mBeanServer.invoke(diagnosticObjectName, STOP_COMMAND
                , new Object[]{new String[]{"name=" + recordingsName}}
                , new String[]{String[].class.getName()});

        System.out.println(reply);
    }

    public static void dumpRecording(String dumpFolder, String recordingsName)
            throws MalformedObjectNameException, InstanceNotFoundException, MBeanException, ReflectionException {
        ObjectName diagnosticObjectName = ObjectName.getInstance(DIAGNOSTIC_COMMAND);

        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        String timeSuffix = '-' + dateFormat.format(new Date());
        File dumpFile = new File(dumpFolder, DUMP_FILE_NAME + timeSuffix + DUMP_FILE_ENDING);
        String reply = (String) mBeanServer.invoke(diagnosticObjectName, DUMP_COMMAND
                , new Object[]{new String[]{"filename=" + dumpFile.getAbsolutePath()
                        , "name=" + recordingsName, "compress=true"}}
                , new String[]{String[].class.getName()});

        System.out.println(reply);
    }
}
