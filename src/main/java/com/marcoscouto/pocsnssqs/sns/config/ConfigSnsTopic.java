package com.marcoscouto.pocsnssqs.sns.config;

import com.marcoscouto.pocsnssqs.sns.service.SnsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Slf4j
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
