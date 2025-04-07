package org.example.repository.german.verb;

import org.example.constants.filepath.german.verb.VerbGermanFilePathConstants;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class VerbRepo {

    public static List<String> getAllVerbsFromFile() {
        try {
            return Files.readAllLines(Paths.get(VerbGermanFilePathConstants.RANDOM_VERB_PATH));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
