package com.marcoscouto.pocsnssqs;

import com.marcoscouto.pocsnssqs.sns.producer.SnsProducer;
import com.marcoscouto.pocsnssqs.sns.service.SnsService;
import com.marcoscouto.pocsnssqs.sqs.producer.SqsProducer;
import com.marcoscouto.pocsnssqs.sqs.service.SqsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import java.util.Arrays;

@Slf4j
@EnableScheduling
@SpringBootApplication
public class PocSnsSqsApplication implements CommandLineRunner {

    private final SqsProducer sqsProducer;

    private final SnsProducer snsProducer;
    private final SnsService snsService;
    private final SqsService sqsService;

    public PocSnsSqsApplication(SqsProducer sqsProducer, SnsProducer snsProducer, SnsService snsService, SqsService sqsService) {
        this.sqsProducer = sqsProducer;
        this.snsProducer = snsProducer;
        this.snsService = snsService;
        this.sqsService = sqsService;
    }

    public static void main(String[] args) {
        SpringApplication.run(PocSnsSqsApplication.class, args);
    }

    @Override
    public void run(String... args) {

        var email = Arrays.asList(args).stream().findFirst();

        // subscribe if email is present in args (careful, email notification is charged by aws)
        email.ifPresent(snsService::subscribeEmail);

        // subscribe sqs queue in sns
        snsService.subscribeSqsQueue(sqsService.getQueueArn());

        // send sqs message
        sqsProducer.sendMessage("This is a SQS message");

        // send sns notification
        snsProducer.sendNotification("This is a SNS message");

    }

}
