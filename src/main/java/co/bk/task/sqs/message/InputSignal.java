package co.bk.task.sqs.message;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

@Value
@AllArgsConstructor
public class InputSignal implements Serializable {

    private String name;

    private String occured_at;
}
