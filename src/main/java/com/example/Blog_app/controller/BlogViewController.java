package com.example.Blog_app.controller;

import com.example.Blog_app.domain.Post;
import com.example.Blog_app.service.BlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

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
}
