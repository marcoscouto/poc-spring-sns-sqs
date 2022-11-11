package com.marcoscouto.pocsnssqs.sns.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sns.SnsClient;

import java.net.URI;
import java.net.URISyntaxException;

import static software.amazon.awssdk.regions.Region.SA_EAST_1;

@Configuration
public class ConfigSnsClient {

    @Value("${aws.sns.host}")
    private String awsSnsHost;

    @Bean
    public SnsClient configSNSClient() throws URISyntaxException {
        return SnsClient.builder()
            .endpointOverride(new URI(this.awsSnsHost))
            .region(SA_EAST_1)
            .build();
    }

}
