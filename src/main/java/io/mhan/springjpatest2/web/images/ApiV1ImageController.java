package io.mhan.springjpatest2.web.images;

import io.mhan.springjpatest2.images.response.ImageDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Tag(name = "ApiV1ImageController", description = "이미지 CRUD")
public interface ApiV1ImageController {
    @Operation(summary = "게시글에서 이미지 생성")
    ResponseEntity<ImageDto> uploadForPost(MultipartFile file, UUID postId);
}
