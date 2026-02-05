package com.sanjai.ems.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.sanjai.ems.dto.EmployeeDTO;
import com.sanjai.ems.entity.Employee;
import com.sanjai.ems.exception.EmployeeNotFoundException;
import com.sanjai.ems.mapper.EmployeeMapper;
import com.sanjai.ems.repository.EmployeeRepository;

@Service
public class EmployeeServiceImpl implements EmployeeService {

    private static final Logger log =
            LoggerFactory.getLogger(EmployeeServiceImpl.class);

    @Autowired
    private EmployeeRepository employeeRepository;

    @Override
    public EmployeeDTO createEmployee(EmployeeDTO employeeDTO) {
        log.info("Creating new employee");

        Employee employee = EmployeeMapper.toEntity(employeeDTO);
        Employee savedEmployee = employeeRepository.save(employee);
        return EmployeeMapper.toDTO(savedEmployee);
    }

    @Override
    public Page<EmployeeDTO> getAllEmployees(Pageable pageable) {
        log.info("Fetching employees with pagination");
        return employeeRepository.findAll(pageable)
                .map(EmployeeMapper::toDTO);
    }

    @Override
    public EmployeeDTO getEmployeeById(Long id) {
        log.info("Fetching employee with id {}", id);

        Employee employee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found with id: " + id));
        return EmployeeMapper.toDTO(employee);
    }

    @Override
    public EmployeeDTO updateEmployee(Long id, EmployeeDTO employeeDTO) {
        log.info("Updating employee with id {}", id);

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found with id: " + id));

        existingEmployee.setName(employeeDTO.getName());
        existingEmployee.setEmail(employeeDTO.getEmail());
        existingEmployee.setDepartment(employeeDTO.getDepartment());

        Employee updatedEmployee = employeeRepository.save(existingEmployee);
        return EmployeeMapper.toDTO(updatedEmployee);
    }

    @Override
    public void deleteEmployee(Long id) {
        log.info("Deleting employee with id {}", id);

        Employee existingEmployee = employeeRepository.findById(id)
                .orElseThrow(() ->
                        new EmployeeNotFoundException("Employee not found with id: " + id));
        employeeRepository.delete(existingEmployee);
    }

    // 🔍 Search by name
    @Override
    public List<EmployeeDTO> searchByName(String name) {
        log.info("Searching employees by name: {}", name);

        return employeeRepository.findByNameContainingIgnoreCase(name)
                .stream()
                .map(EmployeeMapper::toDTO)
                .toList();
    }

    // 🔍 Search by department
    @Override
    public List<EmployeeDTO> searchByDepartment(String department) {
        log.info("Searching employees by department: {}", department);

        return employeeRepository.findByDepartmentContainingIgnoreCase(department)
                .stream()
                .map(EmployeeMapper::toDTO)
                .toList();
    }
}
