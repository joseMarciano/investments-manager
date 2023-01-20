package com.investment.managment.config.aws;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class AWSConfig {

    private final String accessKey;
    private final String secretKey;

    public AWSConfig(@Value("${aws.accessKey}") final String accessKey, @Value("${aws.secretKey}") final String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }


    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(this.accessKey, this.secretKey));
    }
}
