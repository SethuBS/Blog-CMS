package com.blogcms.backend.enums;

import lombok.Getter;

@Getter
public enum Role {
    ADMIN("ADMIN"),
    EDITOR("EDITOR"),
    VIEWER("VIEWER");

    private final String value;

    Role(String value) {
        this.value = value;
    }
}
