package org.example.utils.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class FileUtils {

    public static void saveBytesAsFile(byte[] bytesData, String filePath) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(bytesData);
        }
    }

    public static byte[] getBytesAsFile(String filePath) throws Exception {
        try {
            Path path = Paths.get(filePath);
            return Files.readAllBytes(path);
        }catch (NoSuchFileException | FileNotFoundException noSuchFileException){
            return null;
        }
    }

    public static List<String> getAllFileNamesFromFolder(String folderPath) {
        List<String> fileNames = new ArrayList<>();
        File folder = new File(folderPath);

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileNames.add(file.getName());
                }
            }
        }

        return fileNames;
    }

    public static void createDirectoryIfNotExists(String directoryPath) throws Exception {
        Path path = Paths.get(directoryPath);

        // Check if the directory exists
        if (Files.notExists(path)) {
                // Create the directory and its parents if necessary
                Files.createDirectories(path);
        }
    }
}
