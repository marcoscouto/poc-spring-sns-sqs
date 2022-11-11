# POC AMAZON SNS AND SQS COMMUNICATION

### Prerequisites

- [Java 17+](https://www.oracle.com/java/technologies/javase/jdk17-archive-downloads.html)
- [Gradle 7.5.1+](https://gradle.org/) - or use gradle wrapper
- [AWS CLI 2.8.5+](https://aws.amazon.com/pt/cli/)
- [Docker 20.10.21+ and Docker Compose v2.12.2+](https://www.docker.com/) - if you'll use localstack

### Configuration

Before run this project you need:

- Configure aws credentials and region, [following the tutorial](https://docs.aws.amazon.com/cli/latest/userguide/cli-configure-quickstart.html)

- (optional) Execute docker-compose to initialize [localstack](https://github.com/localstack/localstack).

### How this project works

This project creates automatically a sns topic named poc-sns, a queue named poc-sqs and a subscription sqs queue to sns topic. 
You can change this names on application.yaml file.

The project also have:

- A sns producer that produces a message consumed by the queue subscribed;
- A sqs producer that produces a message listened by this service;
- Three different types of sqs consumers;

When you start the project you are able to see the logs that shows producers and consumers working.

**PS:** Default host is configure to localstack, you can change it replacing the environments variables on application.yaml file.

### Pay attention if you'll use AWS environment

- Some AWS services are free to use until certain limit, but before run this project is really important [check the pricing policies on official site.](https://aws.amazon.com/pt/free/?nc2=h_ql_pr_ft&all-free-tier.sort-by=item.additionalFields.SortRank&all-free-tier.sort-order=asc&awsf.Free%20Tier%20Types=*all&awsf.Free%20Tier%20Categories=*all) 

- After use this project you need to delete or inactivate the services created for this project.
