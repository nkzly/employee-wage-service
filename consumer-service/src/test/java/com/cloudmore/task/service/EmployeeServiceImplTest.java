package com.cloudmore.task.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import com.cloudmore.task.configuration.TaxConfiguration;
import com.cloudmore.task.entity.EmployeeWageEntity;
import com.cloudmore.task.mapper.EmployeeWageMapper;
import com.cloudmore.task.model.EmployeeWage;
import com.cloudmore.task.model.Message;
import com.cloudmore.task.repository.EmployeeWageRepository;
import java.math.BigDecimal;
import java.util.UUID;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class EmployeeServiceImplTest {
    public static final double TAX_RATE = 1.1;

    @Mock
    private TaxConfiguration taxConfiguration;
    @Mock
    private EmployeeWageRepository repository;
    @Mock
    private EmployeeWageMapper mapper;
    @Captor
    private ArgumentCaptor<EmployeeWageEntity> entityCaptor;
    @InjectMocks
    private EmployeeServiceImpl subject;

    @Test
    void saveWageShouldPass() {
        var message = createMessage();
        var entity = createEntity(message);

        doReturn(BigDecimal.valueOf(TAX_RATE)).when(taxConfiguration).getRate();
        doReturn(entity).when(mapper).mapToEntity(any(Message.class));
        doReturn(null).when(repository).save(entityCaptor.capture());

        subject.saveWage(message);

        verify(taxConfiguration, times(1)).getRate();
        verify(mapper, times(1)).mapToEntity(any(Message.class));
        verify(repository, times(1)).save(any(EmployeeWageEntity.class));

        assertEquals(
            message.getMessageBody().getWage().multiply(BigDecimal.valueOf(TAX_RATE)),
            entityCaptor.getValue().getWage()
        );
    }

    private EmployeeWageEntity createEntity(Message message) {
        return EmployeeWageEntity.builder()
           .wage(message.getMessageBody().getWage())
           .id(message.getMessageId())
           .build();
    }

    private Message createMessage() {
        final EmployeeWage employeeWage = new EmployeeWage();
        employeeWage.setWage(BigDecimal.TEN);

        return Message.builder()
            .messageBody(employeeWage)
            .messageId(UUID.randomUUID().toString())
            .build();
    }
}