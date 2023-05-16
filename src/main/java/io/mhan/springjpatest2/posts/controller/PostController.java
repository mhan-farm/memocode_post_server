package io.mhan.springjpatest2.posts.controller;

import io.mhan.springjpatest2.posts.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class PostController {

    private final PostService postService;

    @GetMapping("/posts/new")
    public String showPostCreationForm() {
        return "posts/new";
    }

    @GetMapping("/")
    public String home() {
        return "home";
    }
}
