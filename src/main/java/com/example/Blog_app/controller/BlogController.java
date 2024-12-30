package com.example.Blog_app.controller;

import com.example.Blog_app.domain.Post;
import com.example.Blog_app.dto.AddPostRequest;
import com.example.Blog_app.dto.PostResponse;
import com.example.Blog_app.dto.UpdatePostRequest;
import com.example.Blog_app.service.BlogService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController  //모든 응답 = ResponseBody
public class BlogController {

    private final BlogService blogService;

    public BlogController(BlogService blogService){
        this.blogService = blogService;
    }

    @PostMapping("/api/posts")
    public ResponseEntity<Post> addPost(@RequestBody AddPostRequest request){
        Post savedPost = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(savedPost);
    }

    @GetMapping("/api/posts")
    public ResponseEntity<List<PostResponse>> findAllPosts(){
        List<PostResponse> posts = blogService.findAll()
                .stream()
                .map(PostResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(posts);
    }

    @GetMapping("/api/posts/{id}")
    public ResponseEntity<PostResponse> findPost(@PathVariable long id){
        Post post = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new PostResponse(post));
    }

    @DeleteMapping("/api/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id){
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    @PutMapping("/api/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable long id,
                                           @RequestBody UpdatePostRequest request){
        Post updatedPost = blogService.update(id, request);
        return ResponseEntity.ok()
                .body(updatedPost);
    }


}
