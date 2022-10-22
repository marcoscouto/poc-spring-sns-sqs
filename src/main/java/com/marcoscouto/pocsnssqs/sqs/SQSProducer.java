package com.marcoscouto.pocsnssqs.sqs;

import com.marcoscouto.pocsnssqs.sqs.data.DefaultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.GetQueueUrlRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import static org.springframework.util.Assert.notNull;

@Slf4j
@Component
public class SQSProducer {

    private final SqsClient sqsClient;

    @Value("${aws.sqs.queue.name}")
    private String queueName;

    public SQSProducer(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    public void sendMessage(String message) {
        notNull(message, "message doesn't be empty");
        var defaultMessage = new DefaultMessage(message);
        log.info("[SQS] sending message: {}", defaultMessage.toJSON());
        var request = SendMessageRequest.builder()
            .queueUrl(getQueueUrl())
            .messageBody(defaultMessage.toJSON())
            .build();
        sqsClient.sendMessage(request);
    }

    private String getQueueUrl() {
        var queue = GetQueueUrlRequest.builder().queueName(this.queueName).build();
        return sqsClient.getQueueUrl(queue).queueUrl();
    }

}
