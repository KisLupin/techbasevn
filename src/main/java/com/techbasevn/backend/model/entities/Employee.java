package com.techbasevn.backend.model.entities;

import com.techbasevn.backend.model.request.EmployeeRequest;
import com.techbasevn.backend.utils.Utils;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(indexes = @Index(columnList = "id, name"))
public class Employee implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(length = 100, nullable = false)
    private String name;
    @Column(length = 300, nullable = false)
    private String address;
    @OneToOne()
    @JoinColumn(name = "PositionId", referencedColumnName = "Id", nullable = false)
    private Position position;

    public Employee(EmployeeRequest employeeRequest, Position position) {
        if (Utils.ObjectIsNull(employeeRequest)){
            return;
        }
        BeanUtils.copyProperties(employeeRequest, this);
        if (Utils.ObjectNonNull(position)){
            this.position = position;
        }
    }
}
