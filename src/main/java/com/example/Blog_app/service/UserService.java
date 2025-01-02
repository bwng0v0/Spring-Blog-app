package com.example.Blog_app.service;

import com.example.Blog_app.domain.User;
import com.example.Blog_app.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void registerUser(User user) {
        // 비밀번호 암호화
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        // 중복 사용자 체크 (옵션)
        if ( userRepository.findByUsername( user.getUsername() ).isPresent() ){
            throw new IllegalArgumentException("이미 존재하는 사용자입니다.");
        }

        userRepository.save(user);
    }
}
