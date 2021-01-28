package com.techbasevn.backend.service.impl;

import com.techbasevn.backend.model.dto.EmployeeDTO;
import com.techbasevn.backend.model.entities.Employee;
import com.techbasevn.backend.model.entities.Position;
import com.techbasevn.backend.model.entities.User;
import com.techbasevn.backend.model.request.EmployeeRequest;
import com.techbasevn.backend.repositories.EmployeeRepository;
import com.techbasevn.backend.repositories.PositionRepository;
import com.techbasevn.backend.service.dao.EmployeeService;
import com.techbasevn.backend.utils.Utils;
import com.techbasevn.backend.enumeration.ErrorCode;
import com.techbasevn.backend.exception.RestApiException;
import com.techbasevn.backend.repositories.UserRepository;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeServiceImpl implements EmployeeService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmployeeServiceImpl.class);
    private final EmployeeRepository employeeRepository;
    private final PositionRepository positionRepository;
    private final PasswordEncoder bCryptPasswordEncoder;
    private final UserRepository userRepository;

    @Cacheable(value = "EmployeeDTO")
    @Override
    public List<EmployeeDTO> getAllEmployees() {
        LOGGER.info("get all employees from database before call cache");
        List<Employee> employeeList = employeeRepository.findAll();
        return employeeList.parallelStream().map(EmployeeDTO::new).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public EmployeeDTO addAEmployee(EmployeeRequest employeeRequest) {
        LOGGER.info("add new a employee");
        if (Utils.ObjectIsNull(employeeRequest)){
            throw new RestApiException(ErrorCode.REQUEST_CAN_NOT_BE_NULL);
        }
        Position position = positionRepository.findById(employeeRequest.getPositionId())
                .orElseThrow(() -> new RestApiException(ErrorCode.POSITION_NOT_EXIST));
        Employee employee = new Employee(employeeRequest, position);
        employeeRepository.save(employee);
        User usernameExisted = userRepository.findByUsername(employeeRequest.getUsername());
        if (Utils.ObjectNonNull(usernameExisted)){
            throw new RestApiException(ErrorCode.USERNAME_EXISTED);
        }
        User user = new User(
                employeeRequest.getUsername(),
                bCryptPasswordEncoder.encode(employeeRequest.getPassword()),
                employee
        );
        userRepository.save(user);
        LOGGER.info("add new a employee success");
        return new EmployeeDTO(employee);
    }
}
