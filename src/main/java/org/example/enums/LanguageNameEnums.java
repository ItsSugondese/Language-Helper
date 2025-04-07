package org.example.enums;

import lombok.Getter;

@Getter
public enum LanguageNameEnums {
    GOLANG(""), ANGULAR("/home/lazybot/.nvm/versions/node/v20.14.0/bin");

    private final String envPath;

    LanguageNameEnums(String envPath) {
        this.envPath = envPath;
    }
}
