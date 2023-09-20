package io.mhan.springjpatest2.web.posts.impl;

import io.mhan.springjpatest2.base.annotation.IsUserOrAdmin;
import io.mhan.springjpatest2.base.requestscope.SecurityUser;
import io.mhan.springjpatest2.posts.dto.PostDto;
import io.mhan.springjpatest2.posts.request.PostCreateRequest;
import io.mhan.springjpatest2.posts.request.PostUpdateRequest;
import io.mhan.springjpatest2.posts.service.PostCommandService;
import io.mhan.springjpatest2.posts.service.PostQueryService;
import io.mhan.springjpatest2.web.posts.ApiV1PostCommandController;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/posts")
@RequiredArgsConstructor
public class ApiV1PostCommandControllerImpl implements ApiV1PostCommandController {

    private final PostQueryService postService;
    private final PostCommandService postCommandService;
    private final SecurityUser securityUser;

    @IsUserOrAdmin
    @PostMapping
    public ResponseEntity<PostDto> newPost(@RequestBody PostCreateRequest request) {

        UUID postId = postCommandService.register(securityUser.getId(), request);

        PostDto postDto = postService.getPostDtoById(postId, securityUser.getId());

        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(postId)
                .toUri();

        return ResponseEntity.created(location).body(postDto);
    }

    @IsUserOrAdmin
    @PatchMapping("/{postId}")
    public ResponseEntity<PostDto> updatePost(@PathVariable UUID postId, @RequestBody PostUpdateRequest request) {

        postCommandService.update(postId, securityUser.getId(), request);

        PostDto postDto = postService.getPostDtoById(postId, securityUser.getId());

        return ResponseEntity.ok(postDto);
    }

    @IsUserOrAdmin
    @DeleteMapping("/{postId}")
    public ResponseEntity<Void> softDeleteMyPost(@PathVariable UUID postId) {

        postCommandService.softDeleteMyPost(postId, securityUser.getId());

        return ResponseEntity.noContent().build();
    }
}
