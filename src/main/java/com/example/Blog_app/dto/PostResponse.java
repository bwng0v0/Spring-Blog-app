package com.example.Blog_app.dto;

import com.example.Blog_app.domain.Post;
import lombok.Getter;

@Getter
public class PostResponse {
    private final String title;
    private final String content;

    public PostResponse(Post post){
        this.title = post.getTitle();
        this.content = post.getContent();
    }

}
