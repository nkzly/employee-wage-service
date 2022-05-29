package com.cloudmore.task.service;

import com.cloudmore.task.configuration.TaxConfiguration;
import com.cloudmore.task.model.Message;
import com.cloudmore.task.entity.EmployeeWageEntity;
import com.cloudmore.task.mapper.EmployeeWageMapper;
import com.cloudmore.task.repository.EmployeeWageRepository;
import java.math.BigDecimal;

import com.cloudmore.task.configuration.TaxConfiguration;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {

    private final TaxConfiguration taxConfiguration;
    private final EmployeeWageRepository repository;
    private final EmployeeWageMapper mapper;

    @Override
    @Transactional
    public void saveWage(Message message) {
        var employeeWageEntity= mapper.mapToEntity(message);
        calculateTaxes(taxConfiguration.getRate(), employeeWageEntity);
        repository.save(employeeWageEntity);
        log.info("message:[{}] saved in the db", message.getMessageId());
    }

    private void calculateTaxes(BigDecimal rate, EmployeeWageEntity employee) {
        final BigDecimal wageWithTaxes = employee.getWage().multiply(rate);
        employee.setWage(wageWithTaxes);
    }
}
