package io.mhan.springjpatest2.posts.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PostController {

    @GetMapping("/posts/new")
    public String showPostCreationForm() {
        return "posts/new";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
