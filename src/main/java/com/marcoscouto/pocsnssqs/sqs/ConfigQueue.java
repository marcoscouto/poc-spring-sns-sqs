package com.marcoscouto.pocsnssqs.sqs;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.CreateQueueRequest;

import javax.annotation.PostConstruct;

@Slf4j
@Configuration
public class ConfigQueue {

    private final SqsClient client;

    @Value("${aws.sqs.queue.name}")
    private String queueName;

    public ConfigQueue(SqsClient client) {
        this.client = client;
    }

    @PostConstruct
    public void createQueue() {
        log.info("[SQS] creating queue: {}", this.queueName);
        var request = CreateQueueRequest.builder().queueName(this.queueName).build();
        var response = this.client.createQueue(request);
        log.info("[SQS] Queue created successfully. Url: {}", response.queueUrl());
    }

}
