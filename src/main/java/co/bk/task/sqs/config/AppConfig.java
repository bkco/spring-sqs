package co.bk.task.sqs.config;


import com.amazonaws.auth.*;
import com.amazonaws.auth.profile.ProfileCredentialsProvider;
import com.amazonaws.services.sqs.AmazonSQSAsync;
import com.amazonaws.services.sqs.AmazonSQSAsyncClientBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.aws.messaging.config.QueueMessageHandlerFactory;
import org.springframework.cloud.aws.messaging.config.SimpleMessageListenerContainerFactory;
import org.springframework.cloud.aws.messaging.config.annotation.SqsClientConfiguration;
import org.springframework.cloud.aws.messaging.config.annotation.SqsConfiguration;
import org.springframework.cloud.aws.messaging.listener.QueueMessageHandler;
import org.springframework.context.annotation.*;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.converter.MessageConverter;

import java.lang.String;
import java.util.ArrayList;
import java.util.List;

@Configuration
@Import({SqsConfiguration.class, SqsClientConfiguration.class})
public class AppConfig {

    @Value("${bkco.aws.accessKey}")
    private String accessKey;

    @Value("${bkco.aws.secretKey}")
    private String secretKey;

    @Value("${cloud.aws.region.static}")
    private String region;

    @Value("${spring.profiles.active:not_production}")
    private String activeProfile;

    @Bean
    public AWSCredentialsProvider awsCredentialsProvider() {
        return new AWSStaticCredentialsProvider(new BasicAWSCredentials(accessKey, secretKey));
    }


    @Bean
    @Primary
    @Qualifier("amazonSQSAsync")
    public AmazonSQSAsync amazonSQSAsync(AWSCredentialsProvider awsCredentialsProvider) {
        return AmazonSQSAsyncClientBuilder.standard()
                .withCredentials(awsCredentialsProvider)
                .withRegion(region)
                .build();
    }

    @Bean
    @Primary
    public SimpleMessageListenerContainerFactory simpleMessageListenerContainerFactory(AmazonSQSAsync amazonSqs, QueueMessageHandlerFactory queueMessageHandlerFactory) {
        SimpleMessageListenerContainerFactory factory = new SimpleMessageListenerContainerFactory();
        factory.setAmazonSqs(amazonSqs);
        factory.setMaxNumberOfMessages(10);
        factory.setWaitTimeOut(10);
        //factory.setQueueMessageHandler(new QueueMessageHandler());
        factory.setQueueMessageHandler(queueMessageHandlerFactory.createQueueMessageHandler());
        return factory;
    }

    /*
     * BACKGROUND:
     *   See AwsConfig:
     *     https://www.programcreek.com/java-api-examples/?code=kalnee/trivor/trivor-master/insights/src/main/java/org/kalnee/trivor/insights/consumer/InsightsQueueConsumer.java#
     *   And:
     *     https://stackoverflow.com/questions/50867684/spring-cloud-sqslistener-messageconversionexception-cannot-convert-from-java
     *     https://askvoprosy.com/voprosy/how-to-modify-the-object-mapper-that-spring-cloud-aws-uses-to-deserialize-sqs-messages
     */
    @Bean
    public QueueMessageHandlerFactory queueMessageHandlerFactory(AmazonSQSAsync amazonSQSAsync, ObjectMapper objectMapper) {

        MappingJackson2MessageConverter jacksonMessageConverter =
          new MappingJackson2MessageConverter();
        jacksonMessageConverter.setSerializedPayloadClass(String.class);
        jacksonMessageConverter.setObjectMapper(objectMapper); // custom ObjectMapper can be plugged in if required
        //jacksonMessageConverter.setStrictContentTypeMatch(true);
        jacksonMessageConverter.setStrictContentTypeMatch(false); // avoids need to send Content-Type when publishing message

        List<MessageConverter> payloadArgumentConverters = new ArrayList<>();
        payloadArgumentConverters.add(jacksonMessageConverter);

        QueueMessageHandlerFactory factory = new QueueMessageHandlerFactory();
        factory.setAmazonSqs(amazonSQSAsync);
        factory.setMessageConverters(payloadArgumentConverters);
        return factory;
    }
}