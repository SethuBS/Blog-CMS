package com.blogcms.backend;


import com.blogcms.backend.controller.PostController;
import com.blogcms.backend.dto.PostDTO;
import com.blogcms.backend.dto.TagDTO;
import com.blogcms.backend.dto.UserDTO;
import com.blogcms.backend.enums.PostStatus;
import com.blogcms.backend.exception.ResourceNotFoundException;
import com.blogcms.backend.service.PostService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PostService postService;

    private PostDTO postDTO1;
    private PostDTO postDTO2;
    private UserDTO author;
    private Set<TagDTO> tags;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        author = new UserDTO(1L, "John Doe", "john@example.com", "@#John#@");
        tags = new HashSet<>(Arrays.asList(new TagDTO(1L, "Tech"), new TagDTO(2L, "Spring")));

        postDTO1 = PostDTO.builder()
                .id(1L)
                .title("Title 1")
                .content("Content 1")
                .author(author)
                .status(PostStatus.PUBLISHED)
                .tags(tags)
                .build();

        postDTO2 = PostDTO.builder()
                .id(2L)
                .title("Title 2")
                .content("Content 2")
                .author(author)
                .status(PostStatus.DRAFT)
                .tags(tags)
                .build();
    }

    // Test for Get All Posts
    @Test
    public void getAllPosts_Success() throws Exception {
        List<PostDTO> posts = Arrays.asList(postDTO1, postDTO2);
        when(postService.getAllPosts()).thenReturn(posts);

        mockMvc.perform(get("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].id", is(1)))
                .andExpect(jsonPath("$[0].title", is("Title 1")))
                .andExpect(jsonPath("$[0].status", is("PUBLISHED")))
                .andExpect(jsonPath("$[1].id", is(2)))
                .andExpect(jsonPath("$[1].title", is("Title 2")))
                .andExpect(jsonPath("$[1].status", is("DRAFT")));

        verify(postService, times(1)).getAllPosts();
    }

    // Test for Get Post by ID - Success
    @Test
    public void getPostById_Success() throws Exception {
        when(postService.getPostById(1L)).thenReturn(postDTO1);

        mockMvc.perform(get("/api/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", is(1)))
                .andExpect(jsonPath("$.title", is("Title 1")))
                .andExpect(jsonPath("$.status", is("PUBLISHED")))
                .andExpect(jsonPath("$.author.name", is("John Doe")))
                .andExpect(jsonPath("$.tags", hasSize(2)))
                .andExpect(jsonPath("$.tags[0].name", is("Tech")));

        verify(postService, times(1)).getPostById(1L);
    }

    // Test for Get Post by ID - Not Found
    @Test
    public void getPostById_NotFound() throws Exception {
        when(postService.getPostById(999L)).thenThrow(new ResourceNotFoundException("Post with given id does not exist"));

        mockMvc.perform(get("/api/posts/{id}", 999L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message", is("Post with given id does not exist")));

        verify(postService, times(1)).getPostById(999L);
    }

    // Test for Create Post - Success
    @Test
    @WithMockUser(roles = {"EDITOR"})
    public void createPost_Success() throws Exception {
        when(postService.createPost(postDTO1)).thenReturn(postDTO1);

        mockMvc.perform(post("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"Title 1\", \"content\": \"Content 1\", \"status\": \"PUBLISHED\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Title 1")))
                .andExpect(jsonPath("$.status", is("PUBLISHED")))
                .andExpect(jsonPath("$.author.name", is("John Doe")))
                .andExpect(jsonPath("$.tags", hasSize(2)));

        verify(postService, times(1)).createPost(postDTO1);
    }

    // Test for Update Post - Success
    @Test
    @WithMockUser(roles = {"EDITOR"})
    public void updatePost_Success() throws Exception {
        when(postService.updatePost(eq(postDTO1), eq(1L))).thenReturn(postDTO1);

        mockMvc.perform(put("/api/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{ \"title\": \"Updated Title\", \"content\": \"Updated Content\", \"status\": \"PUBLISHED\" }"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Title 1")))
                .andExpect(jsonPath("$.status", is("PUBLISHED")))
                .andExpect(jsonPath("$.author.name", is("John Doe")))
                .andExpect(jsonPath("$.tags", hasSize(2)));

        verify(postService, times(1)).updatePost(eq(postDTO1), eq(1L));
    }

    // Test for Delete Post - Success
    @Test
    @WithMockUser(roles = {"ADMIN"})
    public void deletePost_Success() throws Exception {
        doNothing().when(postService).deletePost(1L);

        mockMvc.perform(delete("/api/posts/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(postService, times(1)).deletePost(1L);
    }
}