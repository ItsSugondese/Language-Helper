package org.example.enums;

import lombok.Getter;
import org.example.utils.misc.StringUtils;

@Getter
public enum TranslateEnums {
    GERMAN_TO_ENGLISH(LanguageNameEnums.GERMAN, LanguageNameEnums.ENGLISH, "German To English"),
    ENGLISH_TO_GERMAN(LanguageNameEnums.ENGLISH, LanguageNameEnums.GERMAN, "English To German");

    private final LanguageNameEnums from;
    private final LanguageNameEnums to;
    private final String text;

    TranslateEnums(LanguageNameEnums from, LanguageNameEnums to, String text) {
        this.from = from;
        this.to = to;
        this.text = text;
    }

    public static TranslateEnums getTranslateEnumsFromVal(String val) {
        return TranslateEnums.valueOf(StringUtils.toUpperSeparator(val.trim(), "_"));
    }

}
