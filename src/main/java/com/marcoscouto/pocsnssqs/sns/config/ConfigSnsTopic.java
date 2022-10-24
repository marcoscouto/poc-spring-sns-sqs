package com.marcoscouto.pocsnssqs.sns.config;

import com.marcoscouto.pocsnssqs.sns.service.SnsService;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration
public class ConfigSnsTopic {

    private final SnsService snsService;

    public ConfigSnsTopic(SnsService snsService) {
        this.snsService = snsService;
    }

    @PostConstruct
    public void createTopic() {
        snsService.createTopic();
    }

}
