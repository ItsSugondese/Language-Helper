package org.example.constants.filepath;

import java.io.File;

public class FilePathConstants {
    public static final String PROJECT_ABSOLUTE_PATH = System.getProperty("user.dir");

    public static final String RESOURCE_FOLDER_ABSOLUTE_PATH = PROJECT_ABSOLUTE_PATH + File.separator + "src" + File.separator + "main" + File.separator + "resources";
    public static final String ASSETS_FOLDER_ABSOLUTE_PATH = RESOURCE_FOLDER_ABSOLUTE_PATH + File.separator + "assets";

    public static final String AUDIO_FOLDER_ABSOLUTE_PATH = RESOURCE_FOLDER_ABSOLUTE_PATH + File.separator + "storage" + File.separator + "audio";
    public static final String GERMAN_AUDIO_FOLDER_ABSOLUTE_PATH = AUDIO_FOLDER_ABSOLUTE_PATH + File.separator + "german";
    public static final String ENGLISH_AUDIO_FOLDER_ABSOLUTE_PATH = AUDIO_FOLDER_ABSOLUTE_PATH + File.separator + "english";

}
