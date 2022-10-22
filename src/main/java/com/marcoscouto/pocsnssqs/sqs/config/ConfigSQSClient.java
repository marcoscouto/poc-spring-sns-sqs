package com.marcoscouto.pocsnssqs.sqs.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsClient;

import static software.amazon.awssdk.regions.Region.SA_EAST_1;

@Slf4j
@Configuration
public class ConfigSQSClient {

    @Bean
    public SqsClient configClient() {
        log.info("[SQS] Configuring SqsClient bean");
        return SqsClient.builder()
            .region(SA_EAST_1)
            .build();
    }

}
