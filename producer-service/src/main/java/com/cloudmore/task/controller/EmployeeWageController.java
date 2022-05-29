package com.cloudmore.task.controller;

import com.cloudmore.task.dto.EmloyeeWageResponse;
import com.cloudmore.task.dto.EmployeeWageRequest;
import com.cloudmore.task.service.EmployeeWageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping(value = "api/v1/employee-management")
@RequiredArgsConstructor
public class EmployeeWageController {

    private final EmployeeWageService employeeWageService;

    @PostMapping(path = "/save")
    @ResponseStatus(value = HttpStatus.ACCEPTED)
    public EmloyeeWageResponse saveWage(@RequestBody EmployeeWageRequest employeeWageRequest) {
        log.info("Got request -> {}", employeeWageRequest.toString());
        val messageId = employeeWageService.saveWage(employeeWageRequest);
        return new EmloyeeWageResponse(messageId);
    }

}
