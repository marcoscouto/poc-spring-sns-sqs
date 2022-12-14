package com.marcoscouto.pocsnssqs.sqs.consumer;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@Profile("jms")
public class SqsJmsConsumer {

    @JmsListener(destination = "${aws.sqs.queue.name}")
    public void receiveMessage(String message) {
        log.info("[SQS JMS CONSUMER] received message: {}", message);
    }

}
