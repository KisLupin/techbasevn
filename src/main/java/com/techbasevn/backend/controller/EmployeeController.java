package com.techbasevn.backend.controller;

import com.techbasevn.backend.common.BaseResponse;
import com.techbasevn.backend.model.request.EmployeeRequest;
import com.techbasevn.backend.service.dao.EmployeeService;
import lombok.AllArgsConstructor;
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
    public ResponseEntity<?> addAnEmployee(@RequestBody @Valid EmployeeRequest employeeRequest) {
        return ResponseEntity.ok(new BaseResponse(employeeService.addAEmployee(employeeRequest)));
    }
}
