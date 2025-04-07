package org.example.repository.german.enumpath;

import org.example.constants.filepath.german.noun.NounGermanFilePathConstants;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class EnumPathRepo {
    public static String getEnumPath() {
        String path = null;
        try {
            List<String> lines = Files.readAllLines(Paths.get(NounGermanFilePathConstants.ENUM_PATH));
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
