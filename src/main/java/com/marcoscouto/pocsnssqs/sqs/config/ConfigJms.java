package com.marcoscouto.pocsnssqs.sqs.config;

import com.amazon.sqs.javamessaging.ProviderConfiguration;
import com.amazon.sqs.javamessaging.SQSConnectionFactory;
import com.amazonaws.auth.DefaultAWSCredentialsProviderChain;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.config.DefaultJmsListenerContainerFactory;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.support.destination.DynamicDestinationResolver;
import software.amazon.awssdk.services.sqs.SqsClient;

import static com.amazonaws.services.s3.model.Region.SA_SaoPaulo;
import static javax.jms.Session.CLIENT_ACKNOWLEDGE;

@EnableJms
@Configuration
public class ConfigJms {

    // https://aws.amazon.com/pt/blogs/developer/using-amazon-sqs-with-spring-boot-and-spring-jms/
    // https://docs.aws.amazon.com/pt_br/AWSSimpleQueueService/latest/SQSDeveloperGuide/sqs-jms-client-with-sqs-clients.html
    private final SqsClient sqsClient;

    public ConfigJms(SqsClient sqsClient) {
        this.sqsClient = sqsClient;
    }

    @Bean
    public DefaultJmsListenerContainerFactory jmsListenerContainerFactory() {
        DefaultJmsListenerContainerFactory factory =
            new DefaultJmsListenerContainerFactory();
        factory.setConnectionFactory(this.sqsConnectionFactory());
        factory.setDestinationResolver(new DynamicDestinationResolver());
        factory.setConcurrency("3-10");
        factory.setSessionAcknowledgeMode(CLIENT_ACKNOWLEDGE);
        return factory;
    }

    @Bean
    public JmsTemplate defaultJmsTemplate() {
        return new JmsTemplate(this.sqsConnectionFactory());
    }

    private SQSConnectionFactory sqsConnectionFactory(){
        return new SQSConnectionFactory(new ProviderConfiguration(), sqsClient);
    }

}
