package com.techbasevn.backend.controller;

import com.techbasevn.backend.model.request.LoginRequest;
import com.techbasevn.backend.service.dao.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/app/user")
@AllArgsConstructor
@Validated
public class UserController {
    private final UserService userService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody @Valid LoginRequest loginRequest){
        return userService.login(loginRequest);
    }
}
