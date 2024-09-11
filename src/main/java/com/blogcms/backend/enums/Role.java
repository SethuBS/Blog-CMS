package com.blogcms.backend.enums;

import com.blogcms.backend.deserializer.EnumValue;
import lombok.Getter;

@Getter
public enum Role implements EnumValue {
    ADMIN("ADMIN"),
    EDITOR("EDITOR"),
    VIEWER("VIEWER");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
