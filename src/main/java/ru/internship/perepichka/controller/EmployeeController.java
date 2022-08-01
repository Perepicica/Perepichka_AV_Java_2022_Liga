package ru.internship.perepichka.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.internship.perepichka.dto.EmployeeDTO;
import ru.internship.perepichka.entity.Employee;
import ru.internship.perepichka.service.implementation.EmployeeServiceImpl;

import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/employees")
public class EmployeeController {


    private final EmployeeServiceImpl employeeServiceImpl;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<EmployeeDTO>> getAllEmployees() {
        List<EmployeeDTO> employees = employeeServiceImpl.getAllEmployees()
                .stream()
                .map(employee -> modelMapper.map(employee, EmployeeDTO.class))
                .toList();
        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<EmployeeDTO> getEmployeeById(@PathVariable(name = "id") String id) {
        Optional<Employee> employee = employeeServiceImpl.getEmployeeById(id);
        if (employee.isPresent()) {
            EmployeeDTO employeeResponse = modelMapper.map(employee.get(), EmployeeDTO.class);
            return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> createEmployee(@RequestBody EmployeeDTO employeeDTO) {
        try {
            Employee employeeRequest = modelMapper.map(employeeDTO, Employee.class);
            Employee employee = employeeServiceImpl.createEmployee(employeeRequest);
            EmployeeDTO employeeResponse = modelMapper.map(employee, EmployeeDTO.class);
            return new ResponseEntity<>(employeeResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<EmployeeDTO> updateEmployee(@PathVariable(name = "id") String id, @RequestBody EmployeeDTO employeeDTO) {
        Employee employeeRequest = modelMapper.map(employeeDTO, Employee.class);
        Employee employee = employeeServiceImpl.updateEmployee(id, employeeRequest);
        EmployeeDTO employeeResponse = modelMapper.map(employee, EmployeeDTO.class);
        return ResponseEntity.ok().body(employeeResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteEmployee(@PathVariable(name = "id") String id) {
        try {
            employeeServiceImpl.deleteEmployee(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
