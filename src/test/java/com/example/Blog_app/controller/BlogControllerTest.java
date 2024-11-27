package com.example.Blog_app.controller;

import com.example.Blog_app.domain.Post;
import com.example.Blog_app.dto.AddPostRequest;
import com.example.Blog_app.dto.UpdatePostRequest;
import com.example.Blog_app.repository.BlogRepository;
import com.example.Blog_app.service.BlogService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class BlogControllerTest {

    @Autowired
    protected MockMvc mockMvc;

    @Autowired
    protected ObjectMapper objectMapper;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    BlogRepository blogRepository;
    @Autowired
    private BlogService blogService;

    @BeforeEach
    public void mockMvcSetup(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .build();
        blogRepository.deleteAll();
    }

    @DisplayName("addPost: 블로그 글 추가에 성공한다.")
    @Test
    public void addPost() throws Exception {
        //given
        final String url = "/api/posts";
        final String title = "hellotitle";
        final String content = "content";
        final AddPostRequest userRequest = new AddPostRequest(title,content);
        //객체 json으로 직렬화
        final String requestBody = objectMapper.writeValueAsString(userRequest);

        //when
        //설정한 내용을 바탕으로 요청 전송
        ResultActions result = mockMvc.perform(post(url)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(requestBody));

        //then
        result.andExpect(status().isCreated());

        List<Post> posts = blogRepository.findAll();

        assertThat(posts.size()).isEqualTo(1);
        assertThat(posts.get(0).getTitle()).isEqualTo("hellotitle");
        assertThat(posts.get(0).getContent()).isEqualTo(content);
    }

    @DisplayName("findAllPosts: 글 목록 조회에 성공한다.")
    @Test
    public void findAllPosts() throws Exception {
        //given
        final String url = "/api/posts";
        final String title = "title";
        final String content = "content";

        blogRepository.save(Post.builder()
                .title(title)
                .content(content)
                .build());

        //when
        final ResultActions resultActions = mockMvc.perform(get(url)
                .accept(MediaType.APPLICATION_JSON));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value(content))
                .andExpect(jsonPath("$[0].title").value(title));
    }

    @DisplayName("findPost: 블로그 글 조회에 성공한다.")
    @Test
    public void findPost() throws Exception {
        //given
        final String url = "/api/posts/{id}";
        final String title = "title";
        final String content = "content";

        Post savedPost = blogRepository.save(Post.builder()
                .title(title)
                .content(content)
                .build());

        //when
        final ResultActions resultActions = mockMvc.perform(get(url,savedPost.getId()));

        //then
        resultActions
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").value(content))
                .andExpect(jsonPath("$.title").value(title));
    }

    @DisplayName("deletePost: 블로그 글 삭제에 성공한다.")
    @Test
    public void deletePost() throws Exception {
        //given
        final String url = "/api/posts/{id}";
        final String title = "title";
        final String content = "content";
        Post savedPost = blogRepository.save(Post.builder()
                .title(title)
                .content(content)
                .build());
        //when
        mockMvc.perform(delete(url,savedPost.getId()))
                .andExpect(status().isOk());

        //then
        List<Post> posts = blogService.findAll();
        assertThat(posts).isEmpty();
    }

    @DisplayName("updatePost: 블로그 글 수정에 성공한다.")
    @Test
    public void updatePost() throws Exception {
        //given
        final String url = "/api/posts/{id}";
        final String title = "title";
        final String content = "content";
        Post savedPost = blogRepository.save(Post.builder()
                .title(title)
                .content(content)
                .build());
        final String newTitle = "new title";
        final String newContent = "new content";
        UpdatePostRequest request = new UpdatePostRequest(newTitle, newContent);
        //when
        ResultActions result = mockMvc.perform( put(url,savedPost.getId())
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .content(objectMapper.writeValueAsString(request))
        );
        //then
        result.andExpect(status().isOk());
        Post post = blogRepository.findById(savedPost.getId()).get();
        assertThat(post.getTitle()).isEqualTo(newTitle);
        assertThat(post.getContent()).isEqualTo(newContent);
    }
    //210

}