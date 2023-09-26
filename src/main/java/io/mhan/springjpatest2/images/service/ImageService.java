package io.mhan.springjpatest2.images.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import io.mhan.springjpatest2.aws.s3.service.AmazonS3Service;
import io.mhan.springjpatest2.images.response.ImageDto;
import io.mhan.springjpatest2.posts.entity.Post;
import io.mhan.springjpatest2.posts.exception.PostException;
import io.mhan.springjpatest2.posts.service.PostQueryService;
import io.mhan.springjpatest2.users.entity.Author;
import io.mhan.springjpatest2.users.service.AuthorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

import static io.mhan.springjpatest2.base.exception.ErrorCode.CAN_NOT_ACCESS_POST;

@Service
@Transactional
@RequiredArgsConstructor
public class ImageService {
    private final static String IMAGE_FOLDER_NAME = "images";

    private final static String POST_FOLDER_NAME = "posts";
    private final static String IMAGE_NAME = "image";

    private final AmazonS3Service amazonS3Service;
    private final AuthorService authorService;
    private final PostQueryService postQueryService;

    @Value("${custom.cdn.endPoint}")
    private String endPoint;

    private String upload(MultipartFile file, UUID imageId, String name) {

        // 예) images/imageId(uuid)/ + [ name = posts/postId(uuid) ] + /uuid/image.png
        String objectName = IMAGE_FOLDER_NAME + "/" + imageId + "/" + name + "/" + UUID.randomUUID() + "/" + IMAGE_NAME;

        ObjectMetadata objectMetadata = new ObjectMetadata();

        return amazonS3Service.imageUpload(file, objectName, objectMetadata);
    }

    public ImageDto uploadForPost(MultipartFile file, UUID authorId, UUID postId) {

        Post post = postQueryService.findActiveByIdElseThrow(postId);
        Author author = authorService.findActiveByIdElseThrow(authorId);

        if (!author.getId().equals(post.getAuthor().getId())) {
            throw new PostException(CAN_NOT_ACCESS_POST);
        }

        String objectName = upload(file, author.getImageId(), POST_FOLDER_NAME + "/" + post.getId());

        String url = endPoint + "/" + objectName;

        return ImageDto.builder()
                .url(url)
                .build();
    }
}
