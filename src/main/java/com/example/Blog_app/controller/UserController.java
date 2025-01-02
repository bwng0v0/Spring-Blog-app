package com.example.Blog_app.controller;

import com.example.Blog_app.domain.User;
import com.example.Blog_app.service.UserService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/")
    public String root(){
        return "redirect:/posts";
    }

    @GetMapping("/hello")
    public String login(){
        return "login";
    }

    @GetMapping("/signup")
    public String signupPage(){
        return "signup";
    }

    @PostMapping("/signup")
    public String processSignup(@Valid @ModelAttribute User user, BindingResult result) {
        if (result.hasErrors()) {
            return "signup"; // 폼 유효성 에러 시 다시 회원가입 페이지로
        }

        userService.registerUser(user);
        return "redirect:/login"; // 회원가입 성공 시 로그인 페이지로 리다이렉트
    }

}
