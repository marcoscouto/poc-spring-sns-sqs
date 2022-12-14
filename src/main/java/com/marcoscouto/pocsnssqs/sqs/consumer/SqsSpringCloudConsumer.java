package com.marcoscouto.pocsnssqs.sqs.consumer;

import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

import static io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy.ON_SUCCESS;

@Slf4j
@Component
@Profile("spring-cloud")
public class SqsSpringCloudConsumer {

    @SqsListener(value = "${aws.sqs.queue.name}", deletionPolicy = ON_SUCCESS)
    public void receiveMessage(@Payload String message, @Headers Map<String, Object> headers) {
        log.info("[SQS CONSUMER SPRING CLOUD] received message {}, with headers {}", message, headers);
    }

}
