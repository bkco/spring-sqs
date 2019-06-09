package co.bk.task.sqs.resource;


import co.bk.task.sqs.service.QueueService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class TriggerMessage {

    private final QueueService queueService;

    @Autowired
    public TriggerMessage(QueueService queueService) {
        this.queueService = queueService;
    }

    @RequestMapping("/send")
    public ResponseEntity sendMessage() {
        queueService.send();
        return ResponseEntity.ok("Success");
    }
}
