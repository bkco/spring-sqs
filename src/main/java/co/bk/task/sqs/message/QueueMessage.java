package co.bk.task.sqs.message;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

// {"subscription_id":"9b267572-eda2-4416-a1de-79abddfa66b8","event_type":"input-signal","occurred_at":"2019-05-16T15:48:16Z","status":"COMPLETE","datasets":[{"name":"item-one","occurred_at":"2019-05-16T15:48:16Z"}]}
// Prefer use of @Value when we do not have to manually instantiate the object for testing purposes...
//@Value
@Data
@AllArgsConstructor
public class QueueMessage {
    private String subscription_id;
    private String event_type;
    private String occured_at;
    private String status;
    private List<InputSignal> datasets;

}
