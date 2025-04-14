package org.example.constants.properties;

import org.example.utils.ConfigLoader;

public class PropertiesGetterConstants {

    public static String elevenLabsApiKeyGetter(){
        return ConfigLoader.loadProperties().getProperty("ELEVEN_LABS_API_KEY");
    }
}
