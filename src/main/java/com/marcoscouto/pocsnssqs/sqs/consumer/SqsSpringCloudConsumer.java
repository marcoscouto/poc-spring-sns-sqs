package com.marcoscouto.pocsnssqs.sqs.consumer;

import io.awspring.cloud.messaging.listener.annotation.SqsListener;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

import static io.awspring.cloud.messaging.listener.SqsMessageDeletionPolicy.ON_SUCCESS;

@Slf4j
@Component
public class SqsSpringCloudConsumer {

    @SqsListener(value = "${aws.sqs.queue.name}", deletionPolicy = ON_SUCCESS)
    public void receiveMessage(@Payload String message, @Headers Map<String, Object> headers) {
        log.info("[SQS CONSUMER SPRING CLOUD] Received message: {}", message);
    }

}
