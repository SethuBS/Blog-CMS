package com.blogcms.backend.repository;

import com.blogcms.backend.enums.PostStatus;
import com.blogcms.backend.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PostRepository extends JpaRepository<Post, Long> {
    List<Post> findByStatus(PostStatus status);
}
