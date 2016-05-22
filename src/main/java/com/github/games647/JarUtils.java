package com.github.games647;

import java.io.File;
import java.net.URISyntaxException;

public class JarUtils {

    public static File getCurrentJar() throws URISyntaxException {
        return new File(JarUtils.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath());
    }
}
