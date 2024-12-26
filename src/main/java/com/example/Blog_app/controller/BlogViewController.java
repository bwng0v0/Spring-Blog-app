package com.example.Blog_app.controller;

import com.example.Blog_app.domain.Post;
import com.example.Blog_app.dto.PostViewResponse;
import com.example.Blog_app.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor //final & nonnull
@Controller
public class BlogViewController {

    private final BlogService blogService;

    @GetMapping("/posts")
    public String getPostList(Model model){
        model.addAttribute("postList",blogService.findAll());
        return "postList";
    }

    @GetMapping("/posts/{id}")
    public String getPost(@PathVariable long id, Model model){
        Post post = blogService.findById(id);
        model.addAttribute("post",post);
        return "postView";
    }

    @GetMapping("/new-post")
    public String newArticle(@RequestParam(required = false) Long id, Model model) {
        if (id == null) {
            model.addAttribute("post", new PostViewResponse());
        } else {
            Post post = blogService.findById(id);
            model.addAttribute("post", new PostViewResponse(post));
        }

        return "newPost";
    }

}
