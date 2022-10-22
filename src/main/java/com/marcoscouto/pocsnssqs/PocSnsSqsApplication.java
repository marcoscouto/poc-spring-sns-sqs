package com.marcoscouto.pocsnssqs;

import com.marcoscouto.pocsnssqs.sqs.SQSProducer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication
public class PocSnsSqsApplication implements CommandLineRunner {

    private final SQSProducer sqsProducer;

    public PocSnsSqsApplication(SQSProducer sqsProducer) {
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
