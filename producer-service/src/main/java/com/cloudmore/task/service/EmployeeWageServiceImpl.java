package com.cloudmore.task.service;

import com.cloudmore.task.configuration.KafkaConfigurationProperties;
import com.cloudmore.task.dto.EmployeeWageRequest;
import com.cloudmore.task.exception.ProducerException;
import java.util.UUID;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeWageServiceImpl implements EmployeeWageService {
    private final KafkaConfigurationProperties kafkaProperties;
    private final KafkaTemplate<String, EmployeeWageRequest> kafkaTemplate;

    @Override
    public String saveWage(EmployeeWageRequest employeeWageRequest) {
        val messageId = UUID.randomUUID().toString();
        try {
            val producerRecord = new ProducerRecord<>(
                kafkaProperties.getTopic(), messageId, employeeWageRequest);
            kafkaTemplate.send(producerRecord);
            log.info("Message sent with id-> {}", messageId);
        } catch (Exception e) {
            throw new ProducerException(e);
        }
        return messageId;
    }
}
