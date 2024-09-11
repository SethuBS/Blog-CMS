package com.blogcms.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class CommentDTO {
    private Long id;
    private PostDTO postDTO;
    private UserDTO userDTO;
    private String content;
}
