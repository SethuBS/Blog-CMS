package com.blogcms.backend.service;

import com.blogcms.backend.dto.PostDTO;

import java.util.List;

public interface PostService {
    PostDTO createPost(PostDTO postDTO);

    List<PostDTO> getAllPosts();

    PostDTO getPostById(Long postId);

    void deletePost(Long postId);
}
