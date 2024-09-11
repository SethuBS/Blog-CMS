package com.blogcms.backend.service.impl;

import com.blogcms.backend.dto.PostDTO;
import com.blogcms.backend.exception.ResourceNotFoundException;
import com.blogcms.backend.map.Mapper;
import com.blogcms.backend.repository.PostRepository;
import com.blogcms.backend.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class PostServiceImpl implements PostService {

    @Autowired
    private PostRepository postRepository;

    @Override
    public PostDTO createPost(PostDTO newPostDTO) {
        var newPost = Mapper.map(newPostDTO);
        var savedPost = postRepository.save(newPost);
        return Mapper.map(savedPost);
    }

    @Override
    public PostDTO updatePost(PostDTO updatedPostDTO, Long postId) {
        var savedPost = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with given Id: " + postId + " does not exist in the system"));

        var postToUpdate = Mapper.map(updatedPostDTO);

        savedPost.setTitle(updatedPostDTO.getTitle());
        savedPost.setContent(postToUpdate.getContent());
        savedPost.setAuthor(postToUpdate.getAuthor());
        savedPost.setStatus(postToUpdate.getStatus());
        savedPost.setTags(postToUpdate.getTags());

        postRepository.save(savedPost);
        return Mapper.map(postToUpdate);
    }

    @Override
    public List<PostDTO> getAllPosts() {
        return postRepository.findAll().stream()
                .map(Mapper::map)
                .collect(Collectors.toList());
    }

    @Override
    public PostDTO getPostById(Long postId) {
        var post = postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with given id: " + postId + " does not exist in the system"));
        return Mapper.map(post);
    }

    @Override
    public void deletePost(Long postId) {
        postRepository.findById(postId)
                .orElseThrow(() -> new ResourceNotFoundException("Post with given id: " + postId + " does not exist in the system"));
        postRepository.deleteById(postId);
    }
}
