package org.example.utils.files;

import org.example.enums.GetType;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
        } catch (NoSuchFileException | FileNotFoundException noSuchFileException) {
            return null;
        }
    }

    public static List<String> getAllFileNamesFromFolder(String folderPath, GetType getType) {
        List<String> fileNames = new ArrayList<>();
        File folder = new File(folderPath);

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    if (getType.equals(GetType.NAME)) {
                        fileNames.add(file.getName());
                    } else if (getType.equals(GetType.WHOLE_PATH)) {
                        fileNames.add(file.getPath());
                    }
                }
            }
        }

        return fileNames;
    }

    public static Map<String, String> getAllFileNamesAndPathFromFolder(String folderPath, boolean excludeExtention) {
        Map<String, String> fileNames = new HashMap<>();
        File folder = new File(folderPath);

        File[] files = folder.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isFile()) {
                    fileNames.put(file.getPath(), excludeExtention? getFilenameWithoutExtension(file.getName()) : file.getName());
                }
            }
        }

        return fileNames;
    }

    public static String getFilenameWithoutExtensionFromFilePath(String path) {
        File file = new File(path);
        String filenameWithExt = file.getName();
        return getFilenameWithoutExtension(filenameWithExt);
    }

    public static String getFilenameWithoutExtension(String fileName) {
        int dotIndex = fileName.lastIndexOf('.');
        return (dotIndex == -1) ? fileName : fileName.substring(0, dotIndex);
    }

    public static File findFileCaseInsensitive(String path) {
        // Split the path into directories and file name
        String[] pathParts = path.split(File.separator);
        String fileName = pathParts[pathParts.length - 1]; // last part is the file name
        StringBuilder currentPath = new StringBuilder();

        // Traverse the directories and find the file case-insensitively
        for (int i = 0; i < pathParts.length - 1; i++) {  // skip the last part (file name)
            currentPath.append(pathParts[i]).append(File.separator);
            File dir = new File(currentPath.toString());
            if (!dir.exists() || !dir.isDirectory()) {
                return null;  // Directory not found
            }
        }

        // Check if the file exists with case-insensitive comparison
        File dir = new File(currentPath.toString());
        for (File file : dir.listFiles()) {
            if (file.getName().equalsIgnoreCase(fileName)) {
                return file; // File found, return it
            }
        }

        return null; // File not found
    }

    public static void writeStringToFile(String content, String filePath) {
        try {
            Path path = Paths.get(filePath);
            Files.write(path, content.getBytes(StandardCharsets.UTF_8));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void moveFile(String sourcePath, String destinationPath) {
        try {
            Path source = Paths.get(sourcePath);
            Path destination = Paths.get(destinationPath);

            // This will replace the file if it already exists at the destination
            Files.move(source, destination, StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
