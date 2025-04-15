package org.example.utils.files;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileUtils {

    public static void saveBytesAsFile(byte[] bytesData, String filePath) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(bytesData);
        }
    }

    public static byte[] getBytesAsFile(String filePath) throws Exception {
        try {
            Path path = Paths.get(filePath);
            return Files.readAllBytes(path);
        }catch (NoSuchFileException | FileNotFoundException noSuchFileException){
            return null;
        }
    }
}
