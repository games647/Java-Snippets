package com.github.games647;

import java.io.File;

public class FileTools {

    private static long getFolderSize(File folder) {
        long size = 0;

        for (File file : folder.listFiles()) {
            if (file == null) {
                continue;
            }

            if (file.isFile()) {
                size += file.length();
            } else {
                size += getFolderSize(file);
            }
        }

        return size;
    }
}
