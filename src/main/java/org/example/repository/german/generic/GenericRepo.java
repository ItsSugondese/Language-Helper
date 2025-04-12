package org.example.repository.german.generic;

import org.example.constants.filepath.german.noun.NounGermanFilePathConstants;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class GenericRepo {

    public static List<String> getAllFromFileAsList(String path) {
        try {
            return Files.lines(Paths.get(path))
                    .filter(line -> !line.trim().isEmpty()) // ✅ skip empty lines (also trims spaces)
                    .collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
