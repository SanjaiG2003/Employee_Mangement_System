package com.sanjai.ems.mapper;

import com.sanjai.ems.dto.EmployeeDTO;
import com.sanjai.ems.entity.Employee;

public class EmployeeMapper {

    public static EmployeeDTO toDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getId(),
                employee.getName(),
                employee.getEmail(),
                employee.getDepartment()
        );
    }

    public static Employee toEntity(EmployeeDTO dto) {
        Employee emp = new Employee();
        emp.setId(dto.getId());
        emp.setName(dto.getName());
        emp.setEmail(dto.getEmail());
        emp.setDepartment(dto.getDepartment());
        return emp;
    }
}
