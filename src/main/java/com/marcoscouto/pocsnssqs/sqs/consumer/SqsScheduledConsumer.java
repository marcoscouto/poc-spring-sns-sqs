package com.marcoscouto.pocsnssqs.sqs.consumer;

import com.marcoscouto.pocsnssqs.sqs.service.SqsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import static java.util.concurrent.TimeUnit.SECONDS;

@Slf4j
@Component
public class SqsScheduledConsumer {

    private final SqsService sqsService;

    public SqsScheduledConsumer(SqsService sqsService) {
        this.sqsService = sqsService;
    }

    @Scheduled(fixedDelay = 1L, timeUnit = SECONDS)
    public void receiveMessage() {

        var messages = sqsService.receiveMessages();

        messages.forEach(message -> {
            log.info("[SQS CONSUMER SCHEDULED] Received message: {}", message);
            sqsService.deleteMessage(message);
            log.info("[SQS CONSUMER SCHEDULED] Deleting message: {}", message);
        });

    }

}
