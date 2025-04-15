package org.example.utils.files;

import org.example.enums.LanguageNameEnums;
import org.example.enums.WordScreenType;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class FilePathDecider {

    public static List<String> getAudioFolderByScreen(WordScreenType wordScreenType) {
        if(wordScreenType.getLanguageNameEnum() == LanguageNameEnums.ENGLISH) {
            return Collections.singletonList(wordScreenType.getAudioPath());
        } else if(wordScreenType.getLanguageNameEnum() == LanguageNameEnums.GERMAN) {
            if(wordScreenType != WordScreenType.GERMAN_RANDOM){
                return Collections.singletonList(wordScreenType.getAudioPath());
            }

            return WordScreenType.getByLanguageNameEnum(LanguageNameEnums.GERMAN).stream().map(WordScreenType::getAudioPath).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }
}