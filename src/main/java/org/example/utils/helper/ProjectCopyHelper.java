package org.example.utils.helper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class ProjectCopyHelper {

    public static void copyAndModifyFiles(String sourceDir, String destDir, String projectName) throws IOException {
        Path sourcePath = Paths.get(sourceDir);
        Path destPath = Paths.get(destDir);

        // Create destination directory if it doesn't exist
        if (!Files.exists(destPath)) {
            Files.createDirectories(destPath);
        }

        // Stream through all files in the source directory
        try (Stream<Path> paths = Files.walk(sourcePath)) {
            paths.forEach(path -> {
                try {
                    Path relativePath = sourcePath.relativize(path);
                    Path targetPath = destPath.resolve(relativePath);

                    if (Files.isDirectory(path)) {
                        Files.createDirectories(targetPath);
                    } else {
                        // Read content, modify as needed, and copy to target

                        String fileName = path.getFileName().toString();
                        if (!fileName.contains(".")) {
                            fileName += ".go";  // Append .go if there's no extension
                        }

                        // Resolve the target path with the updated file name
                        targetPath = targetPath.getParent().resolve(fileName);
                        String content = new String(Files.readAllBytes(path));
                        String modifiedContent = modifyContent(content, projectName); // Modify content here
                        Files.write(targetPath, modifiedContent.getBytes());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    private static String modifyContent(String content, String projectName) {
        // Implement your content modification logic here
        // For example, replacing text or altering formatting
        return content.replace("?pname", projectName);
    }
}
