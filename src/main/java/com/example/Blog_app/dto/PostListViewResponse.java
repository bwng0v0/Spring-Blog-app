package com.example.Blog_app.dto;

import com.example.Blog_app.domain.Post;
import lombok.Getter;

@Getter
public class PostListViewResponse {
    private final Long id;
    private final String title;
    private final String content;

    public PostListViewResponse(Post post){
        this.id = post.getId();
        this.title = post.getTitle();
        this.content = post.getContent();
    }
}
