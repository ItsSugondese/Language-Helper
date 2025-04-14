package org.example.utils.files;

import org.example.constants.filepath.FilePathConstants;
import org.example.constants.filepath.german.GermanFilePathConstants;
import org.example.enums.LanguageNameEnums;
import org.example.enums.WordType;

public class FilePathDecider {

    public static String getFilePathByLanguageForAudio(LanguageNameEnums languageNameEnums, WordType wordType) {
        switch (languageNameEnums) {
            case GERMAN -> {
                if (wordType == WordType.VERB)
                    return GermanFilePathConstants.GERMAN_VERB_AUDIO_FOLDER_ABSOLUTE_PATH;
                else if (wordType == WordType.NOUN)
                    return GermanFilePathConstants.GERMAN_NOUN_AUDIO_FOLDER_ABSOLUTE_PATH;
                else if (wordType == WordType.W_FRAGE)
                    return GermanFilePathConstants.GERMAN_W_FRAGE_AUDIO_FOLDER_ABSOLUTE_PATH;
                else
                    return null;
            }
            case ENGLISH -> {
                return FilePathConstants.ENGLISH_AUDIO_FOLDER_ABSOLUTE_PATH;
            }
            default -> {
                return null;
            }
        }
    }
}
