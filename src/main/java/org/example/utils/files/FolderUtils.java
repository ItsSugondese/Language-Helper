package org.example.utils.files;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FolderUtils {

    public static void createDirectoryIfNotExists(String directoryPath) throws Exception {
        Path path = Paths.get(directoryPath);

        // Check if the directory exists
        if (Files.notExists(path)) {
            // Create the directory and its parents if necessary
            Files.createDirectories(path);
        }
    }

    public static List<String> getSubdirectoriesPath(String path) {
        List<String> directories = new ArrayList<>();
        File parentDir = new File(path);

        if (parentDir.exists() && parentDir.isDirectory()) {
            File[] subDirs = parentDir.listFiles(File::isDirectory);
            if (subDirs != null) {
                for (File dir : subDirs) {
                    directories.add(dir.getPath());
                }
            }
        }

        return directories;
    }

    public static String getLastDirectoryName(String filePathStr) {
        Path filePath = Paths.get(filePathStr);
        Path parent = filePath.getParent();
        if (parent != null) {
            Path lastDir = parent.getFileName();
            if (lastDir != null) {
                return lastDir.toString();
            }
        }
        return null; // or throw an exception if preferred
    }

    public static String getLastPathSegment(String pathStr) {
        Path path = Paths.get(pathStr);
        return path.getFileName() != null ? path.getFileName().toString() : null;
    }
}
