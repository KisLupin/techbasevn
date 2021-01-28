package com.techbasevn.backend.model.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@AllArgsConstructor
public class EmployeeRequest {
    @NotNull
    private String name;
    @NotNull
    private String address;
    @NotNull
    private Integer positionId;
    @NotNull
    private String username;
    @NotNull
    @Length(min = 8)
    private String password;
}
