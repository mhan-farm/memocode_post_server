package io.mhan.springjpatest2.images.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import io.mhan.springjpatest2.aws.s3.service.AmazonS3Service;
import io.mhan.springjpatest2.images.entity.ImageType;
import io.mhan.springjpatest2.images.response.ImageDto;
import io.mhan.springjpatest2.users.entity.Author;
import io.mhan.springjpatest2.users.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static io.mhan.springjpatest2.images.entity.ImageType.POST;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    private final static String IMAGE_FOLDER_NAME = "images";

    private final static String POST_FOLDER_NAME = "posts";

    private final AmazonS3Service amazonS3Service;
    private final AuthorService authorService;

    @Value("${custom.cdn.endPoint}")
    private String endPoint;

    // 예) images/imageId/posts/uuid.png
    private String upload(MultipartFile file, UUID authorId, ImageType type) {

        Author author = authorService.findActiveByIdElseThrow(authorId);

        String name = switch (type) {
            // 예) imageId/posts/uuid
            case POST -> author.getImageId() + "/" + POST_FOLDER_NAME + "/" + UUID.randomUUID();
        };

        ObjectMetadata objectMetadata = new ObjectMetadata();
        objectMetadata.addUserMetadata("userId", String.valueOf(author.getId()));
        objectMetadata.addUserMetadata("imageId", String.valueOf(author.getImageId()));

        String objectName = amazonS3Service.imageUpload(file, IMAGE_FOLDER_NAME + "/" + name, objectMetadata);

        return objectName;
    }

    // 예) posts/uuid.png
    public ImageDto uploadForPost(MultipartFile file, UUID authorId) {
        String objectName = upload(file, authorId, POST);

        String url = endPoint + "/" + objectName;

        return ImageDto.builder()
                .url(url)
                .build();
    }
}
