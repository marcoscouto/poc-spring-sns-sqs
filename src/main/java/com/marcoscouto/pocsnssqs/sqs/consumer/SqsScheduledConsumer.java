package com.marcoscouto.pocsnssqs.sqs.consumer;

import com.marcoscouto.pocsnssqs.sqs.service.SqsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
@Component
@Profile("scheduled")
public class SqsScheduledConsumer {

    private final SqsService sqsService;

    public SqsScheduledConsumer(SqsService sqsService) {
        this.sqsService = sqsService;
    }

    @Scheduled(fixedDelay = 20L, timeUnit = SECONDS)
    public void receiveMessage() {

        var messages = sqsService.receiveMessages();

        messages.forEach(message -> {
            log.info("[SQS CONSUMER SCHEDULED] received message: {}", message.body());
            sqsService.deleteMessage(message);
            log.info("[SQS CONSUMER SCHEDULED] deleting message: {}", message.body());
        });

    }

}
