package com.marcoscouto.pocsnssqs.sqs.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsClient;

import java.net.URI;
import java.net.URISyntaxException;

import static software.amazon.awssdk.regions.Region.SA_EAST_1;

@Slf4j
@Configuration
public class ConfigSQSClient {

    @Value("${aws.sqs.host}")
    private String awsSqsHost;

    @Bean
    public SqsClient configClient() throws URISyntaxException {
        try {
            return SqsClient.builder()
                .endpointOverride(new URI(this.awsSqsHost))
                .region(SA_EAST_1)
                .build();
        } catch (Throwable t){
            log.error("Error when configure sqs client", t);
            throw t;
        }
    }

}
