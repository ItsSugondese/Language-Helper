package org.example.constants.filepath;

import java.io.File;

public class FilePathConstants {
    public static final String PROJECT_ABSOLUTE_PATH = System.getProperty("user.dir");
    public static final String RESOURCE_FOLDER_ABSOLUTE_PATH = PROJECT_ABSOLUTE_PATH + File.separator + "src" + File.separator + "main" + File.separator + "resources";

}
