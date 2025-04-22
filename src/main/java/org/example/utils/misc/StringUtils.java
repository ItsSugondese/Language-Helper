package org.example.utils.misc;

import java.util.*;
import java.util.stream.Collectors;

public class StringUtils {


    public static String convertStringFromObject(final Object object) {
        return String.valueOf(object);
    }

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
        Map<String, String> result = new LinkedHashMap<>();

        listFromString(string, stringToListDelimiter)
                .stream()
                .map(line -> line.split(stringSeparatorDelimiter, 2))
                .filter(parts -> parts.length == 2)
                .forEach(parts -> {
                    String key = parts[0].trim();
                    String value = parts[1].trim();

                    // Check for existing key (case-insensitive)
                    String existingKey = result.keySet().stream()
                            .filter(k -> k.equalsIgnoreCase(key))
                            .findFirst()
                            .orElse(null);

                    if (existingKey != null) {
                        // Remove the old key if exists
                        result.remove(existingKey);
                    }

                    // Add the new key-value pair (preserving original case of new key)
                    result.put(key, value);
                });

        return result;
    }

    public static String stringFromMapOfString(Map<String, String> map, String keySeparatorDelimiter, String keyValueSeparatorDelimiter) {
        return map.entrySet().stream()
                .map(entry -> entry.getKey() + " " + keyValueSeparatorDelimiter + " " + entry.getValue())
                .collect(Collectors.joining(keySeparatorDelimiter));
    }

    public static String toUpperSeparator(String string, String separator) {
        return string.replaceAll("\\s+", separator).toUpperCase();
    }

    public static String normalizeUmlauts(String input) {
        return input
                .replace("ä", "ae")
                .replace("ö", "oe")
                .replace("ü", "ue")
                .replace("Ä", "Ae")
                .replace("Ö", "Oe")
                .replace("Ü", "Ue")
                .replace("ß", "ss");
    }



}
