package org.example.utils.helper;

import org.example.Main;
import org.example.repository.german.projectname.ProjectNameRepo;
import org.example.utils.StringUtils;

import java.io.*;
import java.util.List;

public class FileWriterHelper {
    public static void readAndWriteFromStorageFileToFile(String storagePath, String filePath, String module, boolean isUUID) {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(storagePath);

        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

                String projectName = ProjectNameRepo.getProjectName();
                String line;
                while ((line = reader.readLine()) != null) {
                    if (line.trim().equals("--noUUID--") && isUUID)
                        break;
                    line = line.replace("?pname", projectName);

                    line = line.replace("?l", StringUtils.toCamelCase(module, '-', false));
                    line = line.replace("?u", StringUtils.toCamelCase(module, '-', true));
                    line = line.replace("?same", module);
                    line = line.replace("?mne", module.replace("-", "_").toUpperCase());
                    line = line.replace("?snake", module.replace("-", "_"));

                    String swagName = module.replace("-", " ");
                    line = line.replace("?swag", Character.toString(swagName.charAt(0)).toUpperCase() + swagName.substring(1));

                    if (line.trim().equals("--noUUID--"))
                        line = "";
                    writer.write(line);
                    writer.newLine(); // Write a newline after each line
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void readAndWriteFromStorageFileToEnumFile(String storagePath, String filePath, String module) {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(storagePath);

        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

                String projectName = ProjectNameRepo.getProjectName();
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.replace("?snake", module.replace("-", "_") + "_enums") ;
                    line = line.replace("?u", StringUtils.toCamelCase(module, '-', true));
                    line = line.replace("?l", StringUtils.toCamelCase(module, '-', false));

                    writer.write(line);
                    writer.newLine(); // Write a newline after each line
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void readAndWriteFromStorageFileToCustomValidationFile(String storagePath, String filePath, String validationName, String moduleName, List<String> validationStrings) {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(storagePath);

        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {

                String projectName = ProjectNameRepo.getProjectName();
                String line;

                StringBuilder toInsertBuilder = new StringBuilder();
                String upperCaseValidationConversion = StringUtils.toCamelCase(validationName, '-', true);
                for(int i=0; i<validationStrings.size(); i++){
                    toInsertBuilder.append("verifiedStatus == enums." + upperCaseValidationConversion + "." + validationStrings.get(i));
                    if(validationStrings.size() - 1 != i)
                        toInsertBuilder.append(" || ");
                }
                while ((line = reader.readLine()) != null) {
                    line = line.replace("?same", moduleName) ;
                    line = line.replace("?pname", projectName) ;
                    line = line.replace("?u", upperCaseValidationConversion);
                    line = line.replace("?insert", toInsertBuilder);
                    line = line.replace("?l", StringUtils.toCamelCase(validationName, '-', false));


                    writer.write(line);
                    writer.newLine(); // Write a newline after each line
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static String readAndWriteFromStorageFileToTextArea(String storagePath, String module) {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(storagePath);
        StringBuilder stringBuilder = new StringBuilder();
        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    line = line.replace("?l", StringUtils.toCamelCase(module, '-', false));
                    line = line.replace("?u", StringUtils.toCamelCase(module, '-', true));
                    line = line.replace("?same", module);
                    line = line.replace("?mne", module.replace("-", "_").toUpperCase());
                    line = line.replace("?snake", module.replace("-", "_"));

                    String swagName = module.replace("-", " ");
                    line = line.replace("?swag", Character.toString(swagName.charAt(0)).toUpperCase() + swagName.substring(1));
                    stringBuilder.append(line + "\n");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

    public static String readAndWriteFromPackagesStorageFileToTextArea(String storagePath) {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream(storagePath);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("go get ");
        if (inputStream != null) {
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line).append(" ");
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return stringBuilder.toString();
    }

}
