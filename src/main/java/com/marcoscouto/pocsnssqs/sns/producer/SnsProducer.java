package com.marcoscouto.pocsnssqs.sns.producer;

import com.marcoscouto.pocsnssqs.sns.service.SnsService;
import com.marcoscouto.pocsnssqs.sqs.data.DefaultMessage;
import org.springframework.stereotype.Component;

@Component
public class SnsProducer {

    private final SnsService snsService;

    public SnsProducer(SnsService snsService) {
        this.snsService = snsService;
    }

    public void sendNotification(String message) {
        var defaultMessage = new DefaultMessage(message);
        snsService.sendNotification(defaultMessage.toJSON());
    }

}
