package com.cloudmore.task.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
    private String messageId;
    private EmployeeWage messageBody;
}
