package io.mhan.springjpatest2.aws.s3.service;

import io.mhan.springjpatest2.aws.s3.repository.AmazonS3Repository;
import io.mhan.springjpatest2.utils.MimeTypeUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class AmazonS3Service {
    private final static String END_POINT = "https://vvovv.cdn.ntruss.com/";
    private final static String BUCKET_NAME = "vvovv";
    private final static String IMAGE_FOLDER_NAME = "i/";

    private final AmazonS3Repository amazonRepository;

    public String imageUpload(MultipartFile file, String name) {

        String mimeType = MimeTypeUtils.getMimeType(file);

        if (!MimeTypeUtils.isImage(mimeType)) {
            throw new IllegalArgumentException("이미지 타입이 아닙니다.");
        }

        String fileExtension = MimeTypeUtils.extractFileExtension(mimeType);

        String objectName = IMAGE_FOLDER_NAME + name + "." + fileExtension;

        amazonRepository.upload(BUCKET_NAME, objectName, file, mimeType);

        String url = END_POINT + objectName;

        return url;
    }
}
