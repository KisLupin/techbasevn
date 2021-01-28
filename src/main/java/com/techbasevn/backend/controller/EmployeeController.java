package com.techbasevn.backend.controller;

import com.techbasevn.backend.model.request.EmployeeRequest;
import com.techbasevn.backend.service.dao.EmployeeService;
import com.techbasevn.backend.common.BaseResponse;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/app/employee")
@Validated
@AllArgsConstructor
public class EmployeeController {
    private final EmployeeService employeeService;

    @GetMapping("/all")
    public ResponseEntity<?> getAllEmployees() {
        return ResponseEntity.ok(new BaseResponse(employeeService.getAllEmployees()));
    }

    @PostMapping("/adding")
    public ResponseEntity<?> addAEmployee(@RequestBody @Valid EmployeeRequest employeeRequest) {
        return ResponseEntity.ok(new BaseResponse(employeeService.addAEmployee(employeeRequest)));
    }
}
