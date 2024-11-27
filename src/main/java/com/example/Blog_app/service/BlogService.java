package com.example.Blog_app.service;

import com.example.Blog_app.domain.Post;
import com.example.Blog_app.dto.AddPostRequest;
import com.example.Blog_app.dto.UpdatePostRequest;
import com.example.Blog_app.repository.BlogRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor //final, @NotNull 필드만 초기화하는 생성자 추가
@Service
public class BlogService {

    private final BlogRepository blogRepository;

    //블로그 글 추가 메서드
    public Post save(AddPostRequest request){
        return blogRepository.save(request.toEntity());
    }

    public List<Post> findAll(){
        return blogRepository.findAll();
    }

    public Post findById(long id){
        return blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+id));
    }

    public void delete(long id){
        blogRepository.deleteById(id);
    }

    @Transactional
    public Post update(long id, UpdatePostRequest request){
        Post post = findById(id);
        post.update(request.getTitle(),request.getContent());
        return post;
    }

}
