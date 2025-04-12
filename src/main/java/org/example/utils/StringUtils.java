package org.example.utils;

import org.example.constants.DelimiterConstants;

import java.util.*;
import java.util.stream.Collectors;

public class StringUtils {

    public static String mergeStringFromList(List<String> listOfString, String delimiter) {
        if (!listOfString.isEmpty()) {
            listOfString = listOfString.stream().distinct().collect(Collectors.toList());
            return String.join(delimiter, listOfString);
        }
        return "";
    }

    public static List<String> listFromString(String string, String delimiter) {
        return new ArrayList<>(Arrays.asList(string.split(delimiter)));
    }

    public static Map<String, String> mapOfStringFromString(String string, String stringSeparatorDelimiter, String stringToListDelimiter) {
        return listFromString(string, stringToListDelimiter)
                .stream()
                .map(line -> line.split(stringSeparatorDelimiter, 2))
                .filter(parts -> parts.length == 2)
                .collect(Collectors.toMap(
                        parts -> parts[0].trim(),
                        parts -> parts[1].trim(),
                        (existing, replacement) -> replacement,
                        LinkedHashMap::new // replace old value with new one
                ));
    }

    public static String stringFromMapOfString(Map<String, String> map, String keySeparatorDelimiter, String keyValueSeparatorDelimiter) {
        return map.entrySet().stream()
                .map(entry -> entry.getKey() + " " + keyValueSeparatorDelimiter + " " + entry.getValue())
                .collect(Collectors.joining(keySeparatorDelimiter));
    }

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
