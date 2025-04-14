package org.example.enums;

import lombok.Getter;

@Getter
public enum TranslateEnums {
    GERMAN_TO_ENGLISH(LanguageNameEnums.GERMAN.name(), LanguageNameEnums.ENGLISH.name(), "German To English"),
    ENGLISH_TO_GERMAN(LanguageNameEnums.ENGLISH.name(), LanguageNameEnums.GERMAN.name(), "English To German");

    private final String from;
    private final String to;
    private final String text;

    TranslateEnums(String from, String to, String text) {
        this.from = from;
        this.to = to;
        this.text = text;
    }

}
