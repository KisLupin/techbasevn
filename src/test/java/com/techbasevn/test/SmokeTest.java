package com.techbasevn.test;

import com.techbasevn.backend.TestApplication;
import com.techbasevn.backend.controller.EmployeeController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TestApplication.class)
public class SmokeTest {

    @Autowired
    private EmployeeController employeeController;

    @Test
    public void contextLoads() {
        assertThat(employeeController).isNotNull();
    }
}
