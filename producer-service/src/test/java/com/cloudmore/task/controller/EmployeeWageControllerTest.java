package com.cloudmore.task.controller;

import com.cloudmore.task.controller.handler.RestExceptionHandler;
import com.cloudmore.task.dto.EmloyeeWageResponse;
import com.cloudmore.task.dto.EmployeeWageRequest;
import com.cloudmore.task.exception.ProducerException;
import com.cloudmore.task.service.EmployeeWageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
class EmployeeWageControllerTest {
    private MockMvc mvc;
    @Mock
    private EmployeeWageService employeeWageService;
    @InjectMocks
    private EmployeeWageController underTest;

    private final String uuid = UUID.randomUUID().toString();
    private final ObjectMapper om = new ObjectMapper()
        .registerModule(new JavaTimeModule());

    @BeforeEach
    public void setup() {
        mvc = MockMvcBuilders.standaloneSetup(underTest)
            .setControllerAdvice(new RestExceptionHandler())
            .build();
    }

    @Test
    @DisplayName("Correct wage request return 202-accepted")
    void saveWageShouldReturn202() throws Exception {
        //given
        final EmloyeeWageResponse expectedResponse = new EmloyeeWageResponse(uuid);
        when(employeeWageService.saveWage(any(EmployeeWageRequest.class)))
            .thenReturn(uuid);
        var req = createRequest();

        //when
        var response = mvc.perform(post("/api/v1/employee-management/save")
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(req)))
            .andExpect(status().isAccepted()).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(202);
        assertThat(response.getContentAsString()).isEqualTo(om.writeValueAsString(expectedResponse));

    }

    @Test
    @DisplayName("Exception in service should throw 500")
    void exceptionInServiceShouldThrow() throws Exception {
        //given
        when(employeeWageService.saveWage(any(EmployeeWageRequest.class)))
            .thenThrow(new ProducerException("e"));
        var req = createRequest();

        //when
        var response = mvc.perform(post("/api/v1/employee-management/save")
            .contentType(MediaType.APPLICATION_JSON)
            .content(om.writeValueAsString(req)))
            .andExpect(status().isInternalServerError()).andReturn().getResponse();

        //then
        assertThat(response.getStatus()).isEqualTo(500);
    }


    private EmployeeWageRequest createRequest() {
        final EmployeeWageRequest employeeWageRequest = new EmployeeWageRequest();
        employeeWageRequest.setName("Nihat");
        employeeWageRequest.setSurname("Kizilay");
        employeeWageRequest.setEventTime(Instant.now());
        employeeWageRequest.setWage(BigDecimal.TEN);
        return employeeWageRequest;
    }
}