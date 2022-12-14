package com.marcoscouto.pocsnssqs.sqs.producer;

import com.marcoscouto.pocsnssqs.sqs.data.DefaultMessage;
import com.marcoscouto.pocsnssqs.sqs.service.SqsService;
import org.springframework.stereotype.Component;

@Component
public class SqsProducer {

    private final SqsService sqsService;

    public SqsProducer(SqsService sqsService) {
        this.sqsService = sqsService;
    }

    public void sendMessage(String message) {
        var defaultMessage = new DefaultMessage(message);
        sqsService.sendMessage(defaultMessage.toJSON());
    }

    public void sendMessageWithTemplate(String message) {
        var defaultMessage = new DefaultMessage(message);
        sqsService.sendMessageWithTemplate(defaultMessage.toJSON());
    }

}
