package org.example.repository.german.modulepath;

import org.example.constants.filepath.german.noun.NounGermanFilePathConstants;
import org.example.enums.LanguageNameEnums;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class ModulePathRepo {
//    public static String getModulePath() {
//        String path = null;
//        try {
//            List<String> lines = Files.readAllLines(Paths.get(FilePathConstants.MODULE_PATH));
//            if (!lines.isEmpty()) {
//                path = lines.get(0); // Get the first line
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//        if (path == null || path.isBlank()) {
//            return null;
//        }
//        return path;
//    }

    public static String getModulePath(LanguageNameEnums languageName) {
        String path = null;
        try {
            List<String> lines = Files.readAllLines(Paths.get(NounGermanFilePathConstants.MODULE_PATH));
            for (String line : lines) {
                if (line.contains(languageName.name())) {
                    String[] paths = line.split(",");
                    if (paths.length > 1) {
                        path =  paths[1];
                    }
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (path == null || path.isBlank()) {
            return null;
        }
        return path;
    }

    public static String setModulePath(File selectedFolder, LanguageNameEnums languageNameEnums) throws IOException {
        String path = selectedFolder.getAbsolutePath();

        List<String> lines = Files.readAllLines(Paths.get(NounGermanFilePathConstants.MODULE_PATH));
        List<String> updatedLines = new ArrayList<>();
        // Iterate through the lines
        for (String line : lines) {
            String[] parts = line.split(",", 2); // Split by "," into two parts (limit split for safety)
            if (parts[0].equals(languageNameEnums.name())) {
                // Update the line at index 1
                line = parts[0] + "," + path;
            }
            updatedLines.add(line);
        }
        Files.write(Paths.get(NounGermanFilePathConstants.MODULE_PATH), updatedLines);
        return path;
    }
}
