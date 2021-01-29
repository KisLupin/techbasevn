package com.techbasevn.test;

import com.techbasevn.backend.TestApplication;
import com.techbasevn.backend.controller.EmployeeController;
import com.techbasevn.backend.controller.UserController;
import com.techbasevn.backend.model.request.EmployeeRequest;
import com.techbasevn.backend.model.request.LoginRequest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(MockitoExtension.class)
@SpringBootTest(classes = TestApplication.class)
class EmployeeControllerTests {
    @Autowired
    EmployeeController employeeController;

    @Autowired
    UserController userController;

    @Test
    public void testAddEmployee() {
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        EmployeeRequest employeeRequest = new EmployeeRequest(
                "abc test", "HCM test", 3, "linhtest", "12345678"
        );
        ResponseEntity<?> responseEntity = employeeController.addAnEmployee(employeeRequest);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void login(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        LoginRequest loginRequest = new LoginRequest(
                 "linhtest", "12345678"
        );
        ResponseEntity<?> responseEntity = userController.login(loginRequest);

        assertThat(responseEntity.getStatusCodeValue()).isEqualTo(200);
    }

    @Test
    public void getAllEmployee(){
        MockHttpServletRequest request = new MockHttpServletRequest();
        RequestContextHolder.setRequestAttributes(new ServletRequestAttributes(request));
        ResponseEntity<?> responseEmployeeEntity = employeeController.getAllEmployees();
        assertThat(responseEmployeeEntity.getStatusCodeValue()).isEqualTo(200);
    }
}
