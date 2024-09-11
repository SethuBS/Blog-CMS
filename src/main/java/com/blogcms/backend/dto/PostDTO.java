package com.blogcms.backend.dto;

import com.blogcms.backend.enums.PostStatus;
import lombok.*;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class PostDTO {
    private Long id;
    private String title;
    private String content;
    private UserDTO author;
    private PostStatus status;
    private Set<TagDTO> tags;
}
