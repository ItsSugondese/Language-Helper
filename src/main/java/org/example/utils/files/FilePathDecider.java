package org.example.utils.files;

import org.example.enums.LanguageNameEnums;
import org.example.enums.WordScreenType;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FilePathDecider {

    public static List<String> getAudioFolderByScreen(WordScreenType wordScreenType) {
        if(wordScreenType.getLanguageNameEnum() == LanguageNameEnums.ENGLISH) {
            return Collections.singletonList(wordScreenType.getAudioPath());
        } else if(wordScreenType.getLanguageNameEnum() == LanguageNameEnums.GERMAN) {
            if(wordScreenType != WordScreenType.GERMAN_RANDOM){
                return Collections.singletonList(wordScreenType.getAudioPath());
            }

            File[] files = new File(WordScreenType.GERMAN_ALL_WORD.getAudioPath()).listFiles();

            List<String> folderName = new ArrayList<>();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                       folderName.add(file.getPath());
                    }
                }
            }
            return folderName;
        }
        return Collections.emptyList();
    }
}