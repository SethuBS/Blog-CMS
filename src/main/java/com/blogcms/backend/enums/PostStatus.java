package com.blogcms.backend.enums;

import lombok.Getter;

@Getter
public enum PostStatus {
    PUBLISHED("ADMIN"),
    DRAFT("EDITOR");

    private final String value;

    PostStatus(String value) {
        this.value = value;
    }
}
