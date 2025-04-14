package org.example.utils.files;

import java.io.FileOutputStream;

public class FileSaveUtils {

    public static void saveBytesAsFile(byte[] bytesData, String filePath) throws Exception {
        try (FileOutputStream fos = new FileOutputStream(filePath)) {
            fos.write(bytesData);
        }
    }
}
