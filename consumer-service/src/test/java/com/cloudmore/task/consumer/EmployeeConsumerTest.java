package com.cloudmore.task.consumer;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.cloudmore.task.model.EmployeeWage;
import com.cloudmore.task.model.Message;
import com.cloudmore.task.service.EmployeeService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeConsumerTest {
    @Mock
    private ObjectMapper om;
    @Mock
    private EmployeeService employeeService;
    @InjectMocks
    private EmployeeConsumer subject;

    @Test
    void consumeShouldPass() {
        doNothing().when(employeeService).saveWage(any(Message.class));

        subject.consume("key", "{}");

        verify(employeeService, times(1)).saveWage(any(Message.class));
    }

    @Test
    @DisplayName("In case of exception of Json processing employeeWageService should not be executed")
    void consumeShouldDoNothingIfThrows() throws JsonProcessingException {
        doThrow(JsonProcessingException.class).when(om).readValue(eq("{}"), eq(EmployeeWage.class));

        subject.consume("key", "{}");

        verify(employeeService, times(0)).saveWage(any(Message.class));
    }
}