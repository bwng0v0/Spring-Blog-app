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
public class BlogApiController {

    private final BlogService blogService;

    public BlogApiController(BlogService blogService){
        this.blogService = blogService;
    }
    //--------- 필드 및 생성자 -----------


    //글 생성
    @PostMapping("/api/posts")
    public ResponseEntity<Post> addPost(@RequestBody AddPostRequest request){
        //@RequestBody로 객체를 받으면 스프링이 자동으로 변환해줌 잭슨 같은거하고 연결해서
        Post savedPost = blogService.save(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedPost);
    }

    //모든 글 조회
    @GetMapping("/api/posts")
    public ResponseEntity<List<PostResponse>> findAllPosts(){
        List<PostResponse> posts = blogService.findAll()
                .stream()
                .map(PostResponse::new)
                .toList();

        return ResponseEntity.ok()
                .body(posts);
    }

    //특정 글 조회
    @GetMapping("/api/posts/{id}")
    public ResponseEntity<PostResponse> findPost(@PathVariable long id){
        Post post = blogService.findById(id);

        return ResponseEntity.ok()
                .body(new PostResponse(post));
    }

    //특정 글 삭제
    @DeleteMapping("/api/posts/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable long id){
        blogService.delete(id);

        return ResponseEntity.ok()
                .build();
    }

    //특정 글 수정
    @PutMapping("/api/posts/{id}")
    public ResponseEntity<Post> updatePost(@PathVariable long id,
                                           @RequestBody UpdatePostRequest request){
        Post updatedPost = blogService.update(id, request);
        return ResponseEntity.ok()
                .body(updatedPost);
    }


}
