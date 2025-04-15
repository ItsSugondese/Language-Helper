package org.example.enums;

import lombok.Getter;
import org.example.constants.filepath.FilePathConstants;
import org.example.constants.filepath.german.GermanFilePathConstants;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum WordScreenType {
    GERMAN_NOUN(GermanFilePathConstants.GERMAN_NOUN_FOLDER_PATH, GermanFilePathConstants.GERMAN_NOUN_AUDIO_FOLDER_ABSOLUTE_PATH, LanguageNameEnums.GERMAN),
    GERMAN_VERB(GermanFilePathConstants.GERMAN_VERB_FOLDER_PATH, GermanFilePathConstants.GERMAN_VERB_AUDIO_FOLDER_ABSOLUTE_PATH, LanguageNameEnums.GERMAN),
    GERMAN_RANDOM(GermanFilePathConstants.RANDOM_FOLDER_PATH, GermanFilePathConstants.GERMAN_RANDOM_AUDIO_FOLDER_ABSOLUTE_PATH, LanguageNameEnums.GERMAN),
    ENGLISH("", FilePathConstants.ENGLISH_AUDIO_FOLDER_ABSOLUTE_PATH, LanguageNameEnums.ENGLISH),
    GERMAN_W_FRAGE(GermanFilePathConstants.GERMAN_W_FRAGE_FOLDER_PATH, GermanFilePathConstants.GERMAN_W_FRAGE_AUDIO_FOLDER_ABSOLUTE_PATH, LanguageNameEnums.GERMAN);

    private final String wordPath;
    private final String audioPath;
    private final LanguageNameEnums languageNameEnum;

    WordScreenType(String wordPath, String audioPath, LanguageNameEnums languageNameEnum) {
        this.wordPath = wordPath;
        this.audioPath = audioPath;
        this.languageNameEnum = languageNameEnum;
    }

    public static List<WordScreenType> getByLanguageNameEnum(LanguageNameEnums language) {
        return Arrays.stream(WordScreenType.values())
                .filter(type -> type.getLanguageNameEnum() == language)
                .collect(Collectors.toList());
    }

}
