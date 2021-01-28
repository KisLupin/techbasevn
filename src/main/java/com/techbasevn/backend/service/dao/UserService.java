package com.techbasevn.backend.service.dao;

import com.techbasevn.backend.model.request.LoginRequest;
import org.springframework.http.ResponseEntity;

public interface UserService {
    ResponseEntity<?> login(LoginRequest loginRequest);
}
