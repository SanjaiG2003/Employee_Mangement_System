package com.sanjai.ems.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.sanjai.ems.dto.EmployeeDTO;

public interface EmployeeService {

    List<EmployeeDTO> searchByName(String name);


    List<EmployeeDTO> searchByDepartment(String department);


    EmployeeDTO createEmployee(EmployeeDTO employeeDTO);

    Page<EmployeeDTO> getAllEmployees(Pageable pageable);

    EmployeeDTO getEmployeeById(Long id);

    EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO);

    void deleteEmployee(Long id);
}

