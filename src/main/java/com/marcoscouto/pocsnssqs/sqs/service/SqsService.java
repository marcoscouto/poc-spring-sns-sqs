package com.marcoscouto.pocsnssqs.sqs.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;
import java.util.Map;

import static java.util.stream.Collectors.joining;
import static org.springframework.util.Assert.notEmpty;
import static org.springframework.util.Assert.notNull;
import static software.amazon.awssdk.services.sqs.model.QueueAttributeName.QUEUE_ARN;

@Slf4j
@Component
public class SqsService {

    private final SqsClient sqsClient;
    @Value("${aws.sqs.queue.name}")
    private String queueName;

    public SqsService(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    public void sendMessage(String message) {
        notNull(message, "message doesn't be null");
        log.info("[SQS PRODUCER] sending message: {}", message);
        var request = SendMessageRequest.builder()
            .queueUrl(getQueueUrl())
            .messageBody(message)
            .build();

        sqsClient.sendMessage(request);
    }

    public List<Message> receiveMessages() {
        var receiveRequest = ReceiveMessageRequest.builder()
            .queueUrl(getQueueUrl())
            .build();

        var messages = sqsClient.receiveMessage(receiveRequest).messages();

        log.info("[SQS] received message: {}", messages.stream().map(Message::body).collect(joining(",")));

        return messages;
    }

    public void deleteMessage(Message message) {
        notNull(message, "message doesn't be null");
        var deleteRequest = DeleteMessageRequest.builder()
            .queueUrl(getQueueUrl())
            .receiptHandle(message.receiptHandle())
            .build();

        log.info("[SQS] deleting message: {}", message);

        sqsClient.deleteMessage(deleteRequest);
    }

    public String getQueueArn() {
        var request = GetQueueAttributesRequest.builder()
            .queueUrl(getQueueUrl())
            .attributeNames(QUEUE_ARN)
            .build();

        return sqsClient.getQueueAttributes(request).attributes().get(QUEUE_ARN);
    }

    public void setQueueAttributes(Map<QueueAttributeName, String> attributes) {
        notEmpty(attributes, "attributes don't be empty");
        var setQueueAttributesRequest = SetQueueAttributesRequest.builder()
            .queueUrl(getQueueUrl())
            .attributes(attributes)
            .build();

        sqsClient.setQueueAttributes(setQueueAttributesRequest);
    }

    public void createQueue() {
        log.info("[SQS] creating queue: {}", this.queueName);

        var request = CreateQueueRequest.builder()
            .queueName(this.queueName)
            .build();

        var response = sqsClient.createQueue(request);
        log.info("[SQS] queue created successfully, url: {}", response.queueUrl());
    }

    private String getQueueUrl() {
        var queue = GetQueueUrlRequest.builder()
            .queueName(this.queueName)
            .build();

        return sqsClient.getQueueUrl(queue).queueUrl();
    }

}
