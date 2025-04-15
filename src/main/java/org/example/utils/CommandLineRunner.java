package org.example.utils;

import org.example.enums.LanguageNameEnums;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Map;

public class CommandLineRunner {

    public static String runCommand(String filePath, String commandGiven) throws Exception{
        String command = "cd " + filePath + " && " + commandGiven;
            // Execute the command
            ProcessBuilder builder = new ProcessBuilder();
            if (System.getProperty("os.name").toLowerCase().contains("win")) {
                builder.command("cmd.exe", "/c", command);
            } else {
                builder.command("bash", "-c", command);
            }

            Process process = builder.start();

            // Read output
            BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            String line;

            StringBuilder stringBuilder = new StringBuilder();

            // Append output to the text area
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line).append("\n");
            }
            while ((line = errorReader.readLine()) != null) {
                stringBuilder.append("ERROR: ").append(line).append("\n");
            }

            int exitCode = process.waitFor();
            stringBuilder.append("\nCommand exited with code: ").append(exitCode);

            return  stringBuilder.toString();
    }

//    public static String runLanguageBasedCommand(String filePath, String commandGiven, LanguageNameEnums languageName) throws Exception {
//        // Build the command
//        String command = "cd " + filePath + " && " + commandGiven;
//
//        // Use ProcessBuilder to execute the command
//        ProcessBuilder builder = new ProcessBuilder();
//
//        // Check if running on Windows or Unix-based system
//        if (System.getProperty("os.name").toLowerCase().contains("win")) {
//            builder.command("cmd.exe", "/c", command);
//        } else {
//            // Include source ~/.bashrc for NVM setup if necessary
//            command = "source ~/.bashrc && " + command;
//            builder.command("bash", "-c", command);
//        }
//
//        // Set environment variables, especially PATH
//        Map<String, String> env = builder.environment();
//        // Update PATH correctly by appending only the directory of ng, not the full ng path
////        env.put("PATH", System.getenv("PATH") + ":" + languageName.getEnvPath()); // Add directory, not the full path
//        env.put("PATH", languageName.getEnvPath() + ":" + System.getenv("PATH"));
//
//
//        env.put("NODE_ENV", "development"); // Optional: Set other environment variables if needed
//
//        // Debug the PATH
//        System.out.println("Modified PATH: " + env.get("PATH"));
//
//        // Start the process
//        Process process = builder.start();
//
//        // Read output
//        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
//        BufferedReader errorReader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
//        String line;
//
//        StringBuilder stringBuilder = new StringBuilder();
//
//        // Append output to the text area
//        while ((line = reader.readLine()) != null) {
//            stringBuilder.append(line).append("\n");
//        }
//        while ((line = errorReader.readLine()) != null) {
//            System.err.println("ERROR: " + line); // Print errors for debugging
//            stringBuilder.append("ERROR: ").append(line).append("\n");
//        }
//
//        int exitCode = process.waitFor();
//        stringBuilder.append("\nCommand exited with code: ").append(exitCode);
//
//        return stringBuilder.toString();
//    }

}
