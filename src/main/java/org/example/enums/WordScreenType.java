package org.example.enums;

import lombok.Getter;
import org.example.constants.filepath.FilePathConstants;
import org.example.constants.filepath.german.GermanFilePathConstants;
import org.example.utils.misc.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public enum WordScreenType {

    GERMAN_RANDOM("German Random", GermanFilePathConstants.RANDOM_FOLDER_PATH, GermanFilePathConstants.RANDOM_AUDIO_FOLDER_ABSOLUTE_PATH, LanguageNameEnums.GERMAN),
    GERMAN_ALL_WORD("German All Word", GermanFilePathConstants.ALL_WORD_FOLDER_PATH, GermanFilePathConstants.ALL_WORD_AUDIO_FOLDER_ABSOLUTE_PATH, LanguageNameEnums.GERMAN),
    ENGLISH("English","", FilePathConstants.ENGLISH_AUDIO_FOLDER_ABSOLUTE_PATH, LanguageNameEnums.ENGLISH);

    private final String text;
    private final String wordPath;
    private final String audioPath;
    private final LanguageNameEnums languageNameEnum;

    WordScreenType(String text, String wordPath, String audioPath, LanguageNameEnums languageNameEnum) {
        this.text = text;
        this.wordPath = wordPath;
        this.audioPath = audioPath;
        this.languageNameEnum = languageNameEnum;
    }

    public static List<WordScreenType> getByLanguageNameEnum(LanguageNameEnums language) {
        return Arrays.stream(WordScreenType.values())
                .filter(type -> type.getLanguageNameEnum() == language)
                .collect(Collectors.toList());
    }

    public static WordScreenType getWordScreenTypeFromVal(String val) {
        return WordScreenType.valueOf(StringUtils.toUpperSeparator(val.trim(), "_"));
    }

}
