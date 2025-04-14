package org.example.utils;

import org.example.constants.filepath.FilePathConstants;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigLoader {
    public static Properties loadProperties() {
        Properties props = new Properties();
        try (FileInputStream input = new FileInputStream(FilePathConstants.RESOURCE_FOLDER_ABSOLUTE_PATH + File.separator + "config.properties")) {
            props.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return props;
    }
}
