package org.example.enums;

import org.example.utils.misc.StringUtils;

public enum LanguageNameEnums {
    GERMAN, ENGLISH;

    public static LanguageNameEnums getFromTranslateEnums(String val){
        return TranslateEnums.getTranslateEnumsFromVal(val).getFrom();
    }
}
