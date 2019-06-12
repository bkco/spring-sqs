package co.bk.task.sqs.service;

import co.bk.task.sqs.message.InputSignal;
import co.bk.task.sqs.message.QueueMessage;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Arrays;

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

        QueueMessage message = new QueueMessage("9b267572-eda2-4416-a1de-79abddfa66b8",
          "input_event",
          "2019-05-16T15:48:16Z",
          "COMPLETED",
          Arrays.asList(new InputSignal("testSignal", "2019-05-16T15:48:16Z"))
        );

        sqsMessageService.sendMessage(queueName, message);
    }
}
