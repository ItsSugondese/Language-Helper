package org.example.repository.german.generic;

import org.example.constants.filepath.german.noun.NounGermanFilePathConstants;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class GenericRepo {

    public static List<String> getAllFromFileAsList(String path) {
        try {
            return Files.readAllLines(Paths.get(path));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
