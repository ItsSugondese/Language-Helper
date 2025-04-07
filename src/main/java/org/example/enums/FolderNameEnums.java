package org.example.enums;

import lombok.Getter;

@Getter
public enum FolderNameEnums {
    REPOSITORY("repo"),
    CONTROLLER("controller"),
    DTO("dto"),
    MODEL("model"),
    ROUTE("route"),
    NAVIGATOR("navigator"),
    SERVICE("service");

    private String name;

    FolderNameEnums(String name) {
        this.name = name;
    }

    public static FolderNameEnums fromName(String name) {
        for (FolderNameEnums folder : FolderNameEnums.values()) {
            if (folder.getName().equalsIgnoreCase(name)) {
                return folder;
            }
        }
        throw new IllegalArgumentException("No enum constant with name " + name);
    }
}
