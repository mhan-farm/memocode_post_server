package io.mhan.springjpatest2.aws.cloudfront.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CloudFrontImageRequest {
    private String originUrl;
}
