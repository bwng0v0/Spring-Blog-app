package com.example.Blog_app.repository;

import com.example.Blog_app.domain.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BlogRepository extends JpaRepository<Post, Long> {
}
