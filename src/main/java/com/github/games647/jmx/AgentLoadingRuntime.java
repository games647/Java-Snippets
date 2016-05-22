package com.github.games647.jmx;

import com.github.games647.JarUtils;
import com.sun.tools.attach.VirtualMachine;

import java.lang.management.ManagementFactory;

public class AgentLoadingRuntime {

    public static void loadAgent(String[] args) {
        String nameOfRunningVM = ManagementFactory.getRuntimeMXBean().getName();
        int p = nameOfRunningVM.indexOf('@');
        String pid = nameOfRunningVM.substring(0, p);

        try {
            VirtualMachine vm = VirtualMachine.attach(pid);
            vm.loadAgent(JarUtils.getCurrentJar().getPath(), "");
            vm.detach();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
