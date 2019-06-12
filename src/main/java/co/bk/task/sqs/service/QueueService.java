package co.bk.task.sqs.service;

import co.bk.task.sqs.message.QueueMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Slf4j
public class QueueService {

    private final SqsMessageService sqsMessageService;

    @Value("${bkco.sqs.queueName}")
    private String queueName;

    public QueueService(final SqsMessageService sqsMessageService) {
        this.sqsMessageService = sqsMessageService;
    }

    public void process(String message) {
        log.info("processing sqs message: {}", message);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    // Send message to SQS queue
    public void send() {
        QueueMessage queueMessage = new QueueMessage();
        queueMessage.setId(UUID.randomUUID().toString().replace("-", ""));
        queueMessage.setMessage("Heh Heh My My.... sqs message");
        sqsMessageService.sendMessage(queueName, queueMessage);
    }
}
