package com.marcoscouto.pocsnssqs.sqs.producer;

import com.marcoscouto.pocsnssqs.sqs.service.SqsService;
import com.marcoscouto.pocsnssqs.sqs.data.DefaultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SqsProducer {

    private final SqsService sqsService;

    public SqsProducer(SqsService sqsService) {
        this.sqsService = sqsService;
    }

    public void sendMessage(String message) {
        var defaultMessage = new DefaultMessage(message);
        log.info("[SQS PRODUCER] sending message: {}", defaultMessage.toJSON());
        sqsService.sendMessage(defaultMessage.toJSON());
    }


}
