package com.example.Blog_app.dto;

import com.example.Blog_app.domain.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
public class PostViewResponse {
    private Long id;
    private String title;
    private String content;
    private LocalDateTime createdAt;

    public PostViewResponse(Post post){
        id = post.getId();
        title = post.getTitle();
        content = post.getContent();
        createdAt = post.getCreatedAt();
    }
}
