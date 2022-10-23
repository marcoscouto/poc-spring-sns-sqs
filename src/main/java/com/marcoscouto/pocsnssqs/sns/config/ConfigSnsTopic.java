package com.marcoscouto.pocsnssqs.sns.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class ConfigSnsTopic {

    @Value("${aws.sns.topic.name}")
    private String topicName;

    private final SnsClient snsClient;

    public ConfigSnsTopic(SnsClient snsClient) {
        this.snsClient = snsClient;
    }

    @PostConstruct
    public void createTopic() {
        log.info("[SNS] creating topic: {}", this.topicName);
        var request = CreateTopicRequest.builder().name(this.topicName).build();
        var response = snsClient.createTopic(request);
        log.info("[SNS] topic created successfully, topic arn: {}", response.topicArn());
    }

}
