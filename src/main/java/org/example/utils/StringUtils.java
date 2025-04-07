package org.example.utils;

public class StringUtils {
    public static String toCamelCase(String str, char separator, boolean toUppercase) {
        StringBuilder result = new StringBuilder();
        String[] parts = str.split(Character.toString(separator));

        if(toUppercase){
            result.append(Character.toUpperCase(parts[0].charAt(0)) + parts[0].substring(1));
        }else{
            result.append(parts[0].toLowerCase());
        }

        // Process the remaining words
        for (int i = 1; i < parts.length; i++) {
            if (parts[i].length() > 0) {
                result.append(Character.toUpperCase(parts[i].charAt(0)));
                result.append(parts[i].substring(1).toLowerCase());
            }
        }

        return result.toString();
    }
}
