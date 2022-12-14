package com.marcoscouto.pocsnssqs.sqs.config;

import com.amazonaws.auth.policy.Policy;
import com.amazonaws.auth.policy.Principal;
import com.amazonaws.auth.policy.Resource;
import com.amazonaws.auth.policy.Statement;
import com.amazonaws.auth.policy.actions.SQSActions;
import com.marcoscouto.pocsnssqs.sqs.service.SqsService;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;

import static java.lang.Boolean.FALSE;
import static software.amazon.awssdk.services.sqs.model.QueueAttributeName.POLICY;
import static software.amazon.awssdk.services.sqs.model.QueueAttributeName.SQS_MANAGED_SSE_ENABLED;

@Configuration
public class ConfigQueue {

    private final SqsService sqsService;

    public ConfigQueue(SqsService sqsService) {
        this.sqsService = sqsService;
    }

    @PostConstruct
    public void createAndConfigureQueue() {

        final String POLICY_NAME = "AllowSnsMessages";
        final String PRINCIPAL_PROVIDER = "Service";
        final String PRINCIPAL_ID = "sns.amazonaws.com";

        sqsService.createQueue();

        var policy = new Policy(POLICY_NAME, List.of(
            new Statement(Statement.Effect.Allow)
                .withActions(SQSActions.AllSQSActions)
                .withPrincipals(new Principal(PRINCIPAL_PROVIDER, PRINCIPAL_ID))
                .withId(POLICY_NAME)
                .withResources(new Resource(sqsService.getQueueArn()))
        ));

        var attributes = Map.of(
            SQS_MANAGED_SSE_ENABLED, FALSE.toString(),
            POLICY, policy.toJson()
        );

        sqsService.setQueueAttributes(attributes);

    }

}
