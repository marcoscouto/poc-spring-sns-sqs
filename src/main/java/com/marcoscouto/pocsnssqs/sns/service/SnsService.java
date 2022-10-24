package com.marcoscouto.pocsnssqs.sns.service;

import com.amazonaws.services.sns.model.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.services.sns.SnsClient;
import software.amazon.awssdk.services.sns.model.CreateTopicRequest;
import software.amazon.awssdk.services.sns.model.PublishRequest;
import software.amazon.awssdk.services.sns.model.SubscribeRequest;

import java.util.Map;

import static org.springframework.util.Assert.notNull;

@Slf4j
@Component
public class SnsService {

    @Value("${aws.sns.topic.name}")
    private String topicName;

    private final SnsClient snsClient;

    public SnsService(SnsClient snsClient) {
        this.snsClient = snsClient;
    }

    public void sendNotification(String message){
        notNull(message, "message not be empty");
        log.info("[SNS] sending message: {}", message);
        var request = PublishRequest.builder()
            .message(message)
            .topicArn(getTopicArn())
            .build();
        snsClient.publish(request);
    }

    public void subscribeEmail(String email) {
        notNull(email, "email not be empty");
        log.info("[SNS] subscribing email: {}", email);
        var request = SubscribeRequest.builder()
            .protocol(SnsProtocol.EMAIL.name())
            .endpoint(email)
            .returnSubscriptionArn(true)
            .topicArn(getTopicArn())
            .build();

        var response = snsClient.subscribe(request);

        log.info("[SNS] email has subscribed successfully, {}", response.sdkHttpResponse().statusText().orElse(email));
    }

    public void subscribeSqsQueue(String sqsArn) {
        notNull(sqsArn, "sqs arn not be empty");
        log.info("[SNS] subscribing sqs queue: {}", sqsArn);
        var request = SubscribeRequest.builder()
            .protocol(SnsProtocol.SQS.name())
            .endpoint(sqsArn)
            .attributes(Map.of("RawMessageDelivery", "true"))
            .returnSubscriptionArn(true)
            .topicArn(getTopicArn())
            .build();

        var response = snsClient.subscribe(request);

        log.info("[SNS] sqs queue has subscribed successfully, arn: {}", response.subscriptionArn());
    }

    public void createTopic() {
        log.info("[SNS] creating topic: {}", this.topicName);
        var request = CreateTopicRequest.builder().name(this.topicName).build();
        var response = snsClient.createTopic(request);
        log.info("[SNS] topic created successfully, topic arn: {}", response.topicArn());
    }

    private String getTopicArn() {
        var result = snsClient.listTopics()
            .topics()
            .stream()
            .filter(topic -> topic.topicArn().contains(this.topicName))
            .findFirst();

        if (result.isEmpty())
            throw new NotFoundException("topic not found " + this.topicName);

        return result.get().topicArn();
    }

    enum SnsProtocol { EMAIL, SQS }

}
