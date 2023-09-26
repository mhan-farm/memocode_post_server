package io.mhan.springjpatest2.web.images;

import io.mhan.springjpatest2.base.annotation.IsUserOrAdmin;
import io.mhan.springjpatest2.base.requestscope.SecurityUser;
import io.mhan.springjpatest2.images.response.ImageDto;
import io.mhan.springjpatest2.images.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ApiV1ImageControllerImpl implements ApiV1ImageController {

    private final ImageService imageService;
    private final SecurityUser securityUser;

    @IsUserOrAdmin
    @PostMapping("/posts/{postId}")
    public ResponseEntity<ImageDto> uploadForPost(@RequestParam("file") MultipartFile file, @PathVariable UUID postId) {
        ImageDto dto = imageService.uploadForPost(file, securityUser.getId(), postId);

        URI location = URI.create(dto.getUrl());
        return ResponseEntity.created(location).body(dto);
    }
}
