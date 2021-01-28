package com.techbasevn.backend.model.dto;

import com.techbasevn.backend.model.entities.Employee;
import com.techbasevn.backend.model.entities.Position;
import com.techbasevn.backend.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDTO implements Serializable {
    private Integer id;
    private String name;
    private String address;
    private String positionCode;
    private String positionDetail;

    public EmployeeDTO(Employee employee) {
        if (Utils.ObjectIsNull(employee)){
            return;
        }
        BeanUtils.copyProperties(employee, this);
        Position position = employee.getPosition();
        if (Utils.ObjectNonNull(position)){
            this.positionCode = position.getCode();
            this.positionDetail = position.getDescription();
        }
    }
}
