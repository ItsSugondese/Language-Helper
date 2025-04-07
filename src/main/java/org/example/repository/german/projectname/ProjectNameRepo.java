package org.example.repository.german.projectname;

import org.example.constants.filepath.german.noun.NounGermanFilePathConstants;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class ProjectNameRepo {
    public static String getProjectName() {
        String path = null;
        try {
            List<String> lines = Files.readAllLines(Paths.get(NounGermanFilePathConstants.PROJECT_NAME_PATH));
            if (!lines.isEmpty()) {
                path = lines.get(0);
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
