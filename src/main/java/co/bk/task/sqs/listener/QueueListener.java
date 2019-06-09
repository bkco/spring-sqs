package co.bk.task.sqs.listener;

import co.bk.task.sqs.service.QueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.aws.messaging.listener.SqsMessageDeletionPolicy;
import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class QueueListener {

    private final QueueService queueService;

    public QueueListener(final QueueService queueService) {
        this.queueService = queueService;
    }

    @SqsListener(value = "myqueue.fifo", deletionPolicy = SqsMessageDeletionPolicy.ON_SUCCESS)
    public void listen(String message, String messageId) {
        log.debug("message received : {}, messageId: {} ", message, messageId);
        queueService.process(message);
    }
}
