package com.cloudmore.task.service;

import com.cloudmore.task.configuration.KafkaConfigurationProperties;
import com.cloudmore.task.dto.EmployeeWageRequest;
import com.cloudmore.task.exception.ProducerException;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EmployeeWageServiceImplTest {
    @Mock
    private KafkaConfigurationProperties kafkaProperties;
    @Mock
    private KafkaTemplate<String, EmployeeWageRequest> kafkaTemplate;
    @InjectMocks
    private EmployeeWageServiceImpl subject;

    @Test
    void saveWageShouldPass() {
        doReturn("topic").when(kafkaProperties).getTopic();

        subject.saveWage(new EmployeeWageRequest());

        verify(kafkaProperties, times(1)).getTopic();
        verify(kafkaTemplate, times(1)).send(any(ProducerRecord.class));
    }

    @Test
    void saveWageShouldThrow() {
        doReturn("topic").when(kafkaProperties).getTopic();
        doThrow(RuntimeException.class).when(kafkaTemplate).send(any(ProducerRecord.class));

        assertThrows(ProducerException.class, () -> subject.saveWage(new EmployeeWageRequest()));
    }

}