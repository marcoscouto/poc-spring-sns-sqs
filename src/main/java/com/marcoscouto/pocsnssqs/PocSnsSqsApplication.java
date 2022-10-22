package com.marcoscouto.pocsnssqs;

import com.marcoscouto.pocsnssqs.sqs.producer.SqsProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class PocSnsSqsApplication implements CommandLineRunner {

    private final SqsProducer sqsProducer;

    public PocSnsSqsApplication(SqsProducer sqsProducer) {
        this.sqsProducer = sqsProducer;
    }

    public static void main(String[] args) {
        SpringApplication.run(PocSnsSqsApplication.class, args);
    }

    @Override
    public void run(String... args) {

        // send sqs message
        sqsProducer.sendMessage("Testing message");

    }

}
