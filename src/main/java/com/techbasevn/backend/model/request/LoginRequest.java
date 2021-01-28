package com.techbasevn.backend.model.request;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
public class LoginRequest {
    @NotNull
    private String username;
    @NotNull
    @Length(min = 8)
    private String password;
    private Boolean rememberMe = false;
}
