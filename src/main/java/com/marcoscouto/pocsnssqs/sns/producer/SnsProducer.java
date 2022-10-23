package com.marcoscouto.pocsnssqs.sns.producer;

import com.marcoscouto.pocsnssqs.sns.service.SnsService;
import com.marcoscouto.pocsnssqs.sqs.data.DefaultMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class SnsProducer {

    private final SnsService snsService;

    public SnsProducer(SnsService snsService) {
        this.snsService = snsService;
    }

    public void sendNotification(String message){
        var defaultMessage = new DefaultMessage(message);
        log.info("[SNS PRODUCER] sending message: {}", defaultMessage.toJSON());
        snsService.sendNotification(defaultMessage.toJSON());
    }

}
