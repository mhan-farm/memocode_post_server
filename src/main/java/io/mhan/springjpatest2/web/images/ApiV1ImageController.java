package io.mhan.springjpatest2.web.images;

import io.mhan.springjpatest2.base.annotation.IsUserOrAdmin;
import io.mhan.springjpatest2.base.requestscope.SecurityUser;
import io.mhan.springjpatest2.images.response.ImageDto;
import io.mhan.springjpatest2.images.service.ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.net.URI;

@RestController
@RequestMapping("/api/v1/images")
@RequiredArgsConstructor
public class ApiV1ImageController {

    private final ImageService imageService;
    private final SecurityUser securityUser;

    @IsUserOrAdmin
    @PostMapping("/posts")
    public ResponseEntity<ImageDto> uploadForPost(@RequestParam("file") MultipartFile file) {

        ImageDto dto = imageService.uploadForPost(file, securityUser.getId());

        URI location = URI.create(dto.getUrl());
        return ResponseEntity.created(location).body(dto);
    }
}
