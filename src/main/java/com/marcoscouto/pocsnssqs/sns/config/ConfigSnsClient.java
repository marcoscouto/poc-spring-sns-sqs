package com.marcoscouto.pocsnssqs.sns.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sns.SnsClient;

import static software.amazon.awssdk.regions.Region.SA_EAST_1;

@Slf4j
@Configuration
public class ConfigSnsClient {

    @Bean
    public SnsClient configSNSClient() {
        return SnsClient.builder()
            .region(SA_EAST_1)
            .build();
    }

}
