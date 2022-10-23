package com.marcoscouto.pocsnssqs.sqs.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

import java.util.List;

import static org.springframework.util.Assert.notNull;
import static software.amazon.awssdk.services.sqs.model.QueueAttributeName.QUEUE_ARN;

@Component
public class SqsService {

    @Value("${aws.sqs.queue.name}")
    private String queueName;

    private final SqsClient sqsClient;

    public SqsService(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    public void sendMessage(String message){
        notNull(message, "message doesn't be empty");
        var request = SendMessageRequest.builder()
            .queueUrl(getQueueUrl())
            .messageBody(message)
            .build();

        sqsClient.sendMessage(request);
    }

    public List<Message> receiveMessages(){
        var receiveRequest = ReceiveMessageRequest.builder()
            .queueUrl(getQueueUrl())
            .build();

       return sqsClient.receiveMessage(receiveRequest).messages();
    }

    public void deleteMessage(Message message){
        notNull(message, "message doesn't be empty");
        var deleteRequest = DeleteMessageRequest.builder()
            .queueUrl(getQueueUrl())
            .receiptHandle(message.receiptHandle())
            .build();

        sqsClient.deleteMessage(deleteRequest);
    }

    public String getQueueArn() {
        var request = GetQueueAttributesRequest.builder()
            .queueUrl(getQueueUrl())
            .attributeNames(QUEUE_ARN)
            .build();

        return sqsClient.getQueueAttributes(request).attributes().get(QUEUE_ARN);
    }

    private String getQueueUrl() {
        var queue = GetQueueUrlRequest.builder()
            .queueName(this.queueName)
            .build();

        return sqsClient.getQueueUrl(queue).queueUrl();
    }

}
