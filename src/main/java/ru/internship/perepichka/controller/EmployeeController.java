package ru.internship.perepichka.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.internship.perepichka.dto.PostPutEmployeeDTO;
import ru.internship.perepichka.dto.GetEmployeeDTO;
import ru.internship.perepichka.entity.Employee;
import ru.internship.perepichka.service.implementation.EmployeeServiceImpl;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("api/employees")
public class EmployeeController {


    private final EmployeeServiceImpl employeeServiceImpl;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<GetEmployeeDTO>> getAllEmployees() {
        List<GetEmployeeDTO> employees = employeeServiceImpl.getAllEmployees()
                .stream()
                .map(employee -> modelMapper.map(employee, GetEmployeeDTO.class))
                .toList();
        if (employees.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(employees, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetEmployeeDTO> getEmployeeById(@PathVariable(name = "id") String id) {
        Optional<Employee> employee = employeeServiceImpl.getEmployeeById(id);
        if (employee.isPresent()) {
            GetEmployeeDTO employeeResponse = modelMapper.map(employee.get(), GetEmployeeDTO.class);
            return new ResponseEntity<>(employeeResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<GetEmployeeDTO> createEmployee(@Valid @RequestBody PostPutEmployeeDTO employeeDTO) {
        try {
            Employee employeeRequest = modelMapper.map(employeeDTO, Employee.class);
            Employee employee = employeeServiceImpl.createEmployee(employeeRequest);
            GetEmployeeDTO employeeResponse = modelMapper.map(employee, GetEmployeeDTO.class);
            return new ResponseEntity<>(employeeResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostPutEmployeeDTO> updateEmployee(@PathVariable(name = "id") String id,
                                                             @Valid @RequestBody PostPutEmployeeDTO employeeDTO) {
        Employee employeeRequest = modelMapper.map(employeeDTO, Employee.class);
        Employee employee = employeeServiceImpl.updateEmployee(id, employeeRequest);
        PostPutEmployeeDTO employeeResponse = modelMapper.map(employee, PostPutEmployeeDTO.class);
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
