package com.example.Blog_app.controller;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDate;
import java.util.List;

@Controller
public class ExampleController {
    @GetMapping("/example")
    public String example(Model model){
        Person person = new Person();
        person.setId(1L);
        person.setName("엄준식");
        person.setAge(11);
        person.setHobbies(List.of("코딩","운동","스프링"));

        model.addAttribute("person", person);
        model.addAttribute("today", LocalDate.now());

        return "example";
    }

    @Setter
    @Getter
    class Person{
        private Long id;
        private String name;
        private int age;
        private List<String> hobbies;
    }

}
