package org.example.repository.german.noun;

import org.example.constants.filepath.german.noun.NounGermanFilePathConstants;
import org.example.constants.filepath.german.verb.VerbGermanFilePathConstants;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;

public class NounRepo {

    public static List<String> getAllNounFromFile() {
        try {
            return Files.readAllLines(Paths.get(NounGermanFilePathConstants.RANDOM_NOUN_PATH));
        } catch (Exception e) {
            e.printStackTrace();
        }

        return Collections.emptyList();
    }
}
