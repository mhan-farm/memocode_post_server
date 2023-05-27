package io.mhan.springjpatest2.aws.s3.config;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AmazonS3Config {

    private final static String END_POINT = "https://kr.object.ncloudstorage.com";
    private final static String REGION_NAME = "kr-standard";
    private final static String ACCESS_KEY = "Psswc54gniaJsY6ACyQ2";
    private final static String SECRET_KEY = "y11PA6K47LhkunL0BIOUoUg5xmVVhs8ItFLRZNRR";

    @Bean
    public AmazonS3 amazonS3() {

        AmazonS3 s3 = AmazonS3ClientBuilder.standard()
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(END_POINT, REGION_NAME))
                .withCredentials(new AWSStaticCredentialsProvider(new BasicAWSCredentials(ACCESS_KEY, SECRET_KEY)))
                .build();

        return s3;
    }
}
