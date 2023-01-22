package com.investment.managment.config.aws;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@AWSContext
public class AWSCredentialsConfig {

    private final String accessKey;
    private final String secretKey;

    public AWSCredentialsConfig(@Value("${aws.accessKey}") final String accessKey, @Value("${aws.secretKey}") final String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }


    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(this.accessKey, this.secretKey));
    }
}
