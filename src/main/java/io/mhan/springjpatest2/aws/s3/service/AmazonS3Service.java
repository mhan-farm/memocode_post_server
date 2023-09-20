package io.mhan.springjpatest2.aws.s3.service;

import com.amazonaws.services.s3.model.ObjectMetadata;
import io.mhan.springjpatest2.aws.s3.properties.AmazonS3Properties;
import io.mhan.springjpatest2.aws.s3.repository.AmazonS3Repository;
import io.mhan.springjpatest2.utils.MimeTypeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AmazonS3Service {

    private final AmazonS3Properties amazonS3Properties;

    private final AmazonS3Repository amazonRepository;

    public String imageUpload(MultipartFile file, String name, ObjectMetadata metadata) {

        String mimeType = MimeTypeUtils.getMimeType(file);

        if (!MimeTypeUtils.isImage(mimeType)) {
            throw new IllegalArgumentException("이미지 타입이 아닙니다.");
        }

        String fileExtension = MimeTypeUtils.extractFileExtension(mimeType);

        String objectName = name + "." + fileExtension;

        amazonRepository.upload(amazonS3Properties.getBucketName(), objectName, file, mimeType, metadata);

        return objectName;
    }
}
