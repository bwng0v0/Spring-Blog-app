package com.example.Blog_app.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Getter//lombok 게터 자동생성
@NoArgsConstructor(access = AccessLevel.PROTECTED)//lombok protected 기본생성자
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;


    @Builder//빌더 패턴으로 객체 생성
    public Post(String title, String content){
        this.title = title;
        this.content = content;
        createdAt = LocalDateTime.now();
    }

    public void update(String title, String content){
        this.title = title;
        this.content = content;
    }

}
