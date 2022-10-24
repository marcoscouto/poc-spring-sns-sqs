package com.marcoscouto.pocsnssqs.sqs.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsClient;

import static software.amazon.awssdk.regions.Region.SA_EAST_1;

@Configuration
public class ConfigSQSClient {

    @Bean
    public SqsClient configClient() {
        return SqsClient.builder()
            .region(SA_EAST_1)
            .build();
    }

}
