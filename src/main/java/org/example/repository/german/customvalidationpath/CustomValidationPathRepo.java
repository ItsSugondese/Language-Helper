package org.example.repository.german.customvalidationpath;

import org.example.constants.filepath.german.noun.NounGermanFilePathConstants;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class CustomValidationPathRepo {
    public static String getCustomValidationPath() {
        String path = null;
        try {
            List<String> lines = Files.readAllLines(Paths.get(NounGermanFilePathConstants.CUSTOM_VALIDATION_PATH));
            if (!lines.isEmpty()) {
                path = lines.get(0); // Get the first line
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (path == null || path.isBlank()) {
            return null;
        }
        return path;
    }
}
