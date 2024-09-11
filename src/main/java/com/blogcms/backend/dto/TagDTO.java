package com.blogcms.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class TagDTO {
    private Long id;
    private String name;
}
