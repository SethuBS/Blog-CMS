package com.blogcms.backend.enums;

import com.blogcms.backend.deserializer.EnumValue;
import lombok.Getter;

@Getter
public enum PostStatus implements EnumValue {
    PUBLISHED("ADMIN"),
    DRAFT("EDITOR");

    private final String value;

    PostStatus(String value) {
        this.value = value;
    }

    @Override
    public String getValue() {
        return value;
    }
}
