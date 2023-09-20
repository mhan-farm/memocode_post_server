package io.mhan.springjpatest2.aws.cloudfront.controller;

import io.mhan.springjpatest2.aws.cloudfront.request.CloudFrontImageRequest;
import io.mhan.springjpatest2.aws.cloudfront.request.CreateCannedPolicyRequest;
import io.mhan.springjpatest2.aws.cloudfront.service.SigningUtilities;
import io.mhan.springjpatest2.base.annotation.IsAdmin;
import io.mhan.springjpatest2.base.response.SuccessResponse;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import software.amazon.awssdk.services.cloudfront.model.CannedSignerRequest;
import software.amazon.awssdk.services.cloudfront.url.SignedUrl;

/**
 * 나중에 사용 예정
 */
@RestController
public class CloudFrontController {
    String privateKeyFullPath = "/Users/myunghan/Desktop/cloudfront/private_key.der";
    String publicKeyId = "K30AB0KXWKYXZ5";

    @IsAdmin // admin만 접근 가능하게 하여 일반 사용자가 사용하지 못하게 함
    @PostMapping("/cloudfront/pre_signed_url")
    public SuccessResponse<String> changeImageUrlToPreSignedUrl(@RequestBody CloudFrontImageRequest request) throws Exception {
        String originUrl = request.getOriginUrl();

        CannedSignerRequest requestForCannedPolicy = CreateCannedPolicyRequest.createRequestForCannedPolicy(
                originUrl,
                privateKeyFullPath,
                publicKeyId
        );

        SignedUrl signedUrl = SigningUtilities.signUrlForCannedPolicy(requestForCannedPolicy);

        return SuccessResponse.ok("url 암호화를 성공하셨습니다.", signedUrl.url());
    }
}
