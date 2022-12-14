package com.marcoscouto.pocsnssqs.sqs.config;

import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import io.awspring.cloud.messaging.core.QueueMessagingTemplate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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

    @Bean
    @Primary
    public AmazonSQSAsync configAwsClient() {
        try {
            return AmazonSQSAsyncClientBuilder.standard()
                .withCredentials(new DefaultAWSCredentialsProviderChain())
                .withEndpointConfiguration(new AwsClientBuilder.EndpointConfiguration(awsSqsHost, SA_EAST_1.toString()))
                .build();
        } catch (Throwable t){
            log.error("Error when configure amazon sqs async client", t);
            throw t;
        }
    }

    @Bean
    public QueueMessagingTemplate queueMessagingTemplate(AmazonSQSAsync amazonSQSAsync) {
        try {
            return new QueueMessagingTemplate(amazonSQSAsync);
        } catch (Throwable t){
            log.error("Error when queue messaging template", t);
            throw t;
        }
    }

}
