package com.techbasevn;

import com.techbasevn.backend.controller.EmployeeController;
import com.techbasevn.backend.model.request.EmployeeRequest;
import com.techbasevn.backend.service.dao.EmployeeService;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@RunWith(SpringRunner.class)
@WebMvcTest(value = EmployeeController.class)
class EmployeeControllerTests {
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private EmployeeService employeeService;

    @Test
    void checkLoginRequest() throws Exception {
        Mockito.when(employeeService.addAEmployee(Mockito.any(EmployeeRequest.class)));
        RequestBuilder requestBuilder = MockMvcRequestBuilders
                .post("/app/employee/adding")
                .accept(MediaType.APPLICATION_JSON)
                .content("example")
                .contentType(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Assert.assertEquals(HttpStatus.OK.value(), response.getStatus());

    }
}
