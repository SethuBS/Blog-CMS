package com.blogcms.backend.map;

import com.blogcms.backend.dto.CommentDTO;
import com.blogcms.backend.dto.PostDTO;
import com.blogcms.backend.dto.TagDTO;
import com.blogcms.backend.dto.UserDTO;
import com.blogcms.backend.model.Comment;
import com.blogcms.backend.model.Post;
import com.blogcms.backend.model.Tag;
import com.blogcms.backend.model.User;

import java.util.Set;
import java.util.stream.Collectors;

public class Mapper {

    public static UserDTO map(User user) {
        if (user == null) {
            return null;
        }

        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .email(user.getEmail())
                .password(user.getPassword())
                .build();
    }

    public static User map(UserDTO userDTO) {
        if (userDTO == null) {
            return null;
        }

        return User.builder()
                .id(userDTO.getId())
                .username(userDTO.getUsername())
                .email(userDTO.getEmail())
                .password(userDTO.getPassword())
                .build();
    }

    public static Post map(PostDTO postDTO) {
        if (postDTO == null) {
            return null;
        }

        Set<Tag> tags = postDTO.getTags().stream()
                .map(Mapper::map)
                .collect(Collectors.toSet());

        return Post.builder()
                .id(postDTO.getId())
                .title(postDTO.getTitle())
                .content(postDTO.getContent())
                .author(map(postDTO.getAuthor()))
                .status(postDTO.getStatus())
                .tags(tags)
                .build();
    }

    public static PostDTO map(Post post) {
        if (post == null) {
            return null;
        }

        Set<TagDTO> tagDTOs = post.getTags().stream()
                .map(Mapper::map)
                .collect(Collectors.toSet());

        return PostDTO.builder()
                .id(post.getId())
                .title(post.getTitle())
                .content(post.getContent())
                .author(map(post.getAuthor()))
                .status(post.getStatus())
                .tags(tagDTOs)
                .build();
    }

    public static Tag map(TagDTO tagDTO) {
        if (tagDTO == null) {
            return null;
        }

        return Tag.builder()
                .id(tagDTO.getId())
                .name(tagDTO.getName())
                .build();
    }

    public static TagDTO map(Tag tag) {
        if (tag == null) {
            return null;
        }

        return TagDTO.builder()
                .id(tag.getId())
                .name(tag.getName())
                .build();
    }

    public static Comment map(CommentDTO commentDTO) {
        if (commentDTO == null) {
            return null;
        }

        return Comment.builder()
                .id(commentDTO.getId())
                .post(map(commentDTO.getPostDTO()))
                .user(map(commentDTO.getUserDTO()))
                .content(commentDTO.getContent())
                .build();
    }

    public static CommentDTO map(Comment comment) {
        if (comment == null) {
            return null;
        }

        return CommentDTO.builder()
                .id(comment.getId())
                .postDTO(map(comment.getPost()))
                .userDTO(map(comment.getUser()))
                .content(comment.getContent())
                .build();
    }

}
