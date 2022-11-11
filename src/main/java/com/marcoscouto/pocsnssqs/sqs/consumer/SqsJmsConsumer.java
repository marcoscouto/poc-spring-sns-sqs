package com.marcoscouto.pocsnssqs.sqs.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SqsJmsConsumer {

    @JmsListener(destination = "${aws.sqs.queue.name}")
    public void receiveMessage(String message) {
        log.info("[SQS JMS CONSUMER] Received message: {}", message);
    }

}
