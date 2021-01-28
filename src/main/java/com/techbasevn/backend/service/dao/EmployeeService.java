package com.techbasevn.backend.service.dao;

import com.techbasevn.backend.model.dto.EmployeeDTO;
import com.techbasevn.backend.model.request.EmployeeRequest;

import java.util.List;

public interface EmployeeService {
    List<EmployeeDTO> getAllEmployees();

    EmployeeDTO addAEmployee(EmployeeRequest employeeRequest);
}
