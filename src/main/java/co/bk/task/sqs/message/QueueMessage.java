package co.bk.task.sqs.message;

import lombok.Data;

@Data
public class QueueMessage {
    private String message;
    private String id;
}
