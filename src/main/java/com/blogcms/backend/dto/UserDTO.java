package com.blogcms.backend.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Builder
public class UserDTO {
    private Long id;
    private String username;
    private String email;
    private String password;
}
