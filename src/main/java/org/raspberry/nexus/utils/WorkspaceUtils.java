package org.raspberry.nexus.utils;

import org.raspberry.nexus.entity.Build;
import org.raspberry.nexus.exception.InternalServerErrorException;

import java.io.File;
import java.util.List;

public class WorkspaceUtils {

    public static File getWorkspace(Build build) {
        return new File("builds", "build-" + build.getId());
    }

    public static void createWorkspace(Build build) {
        File workspace = getWorkspace(build);

        createDirectory(workspace);
    }

    public static void deleteWorkspace(Build build) {
        File workspace = getWorkspace(build);

        deleteDirectory(workspace);
    }

    private static void createDirectory(File directory) {
        if (directory.exists()) {
            throw new InternalServerErrorException("Directory '%s' already exists", directory);
        }

        if (!directory.mkdirs()) {
            throw new InternalServerErrorException("Failed to create directory '%s'", directory);
        }
    }

    private static void deleteDirectory(File directory) {
        if (!directory.exists()) {
            return;
        }

        for (File file : listFiles(directory)) {
            if (file.isDirectory()) {
                deleteDirectory(file);
            } else {
                deleteFile(file);
            }
        }

        if (!directory.delete()) {
            throw new InternalServerErrorException("Failed to delete directory '%s'", directory);
        }
    }

    private static void deleteFile(File file) {
        if (!file.exists()) {
            return;
        }

        if (!file.delete()) {
            throw new InternalServerErrorException("Failed to delete file '%s'", file);
        }
    }

    private static List<File> listFiles(File directory) {
        File[] files = directory.listFiles();

        return files != null
                ? List.of(files)
                : List.of();
    }

}
