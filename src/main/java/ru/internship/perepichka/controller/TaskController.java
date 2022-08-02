package ru.internship.perepichka.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.internship.perepichka.dto.GetTaskDTO;
import ru.internship.perepichka.dto.PostPutTaskDTO;
import ru.internship.perepichka.entity.Employee;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.exception.BadIdException;
import ru.internship.perepichka.service.implementation.EmployeeServiceImpl;
import ru.internship.perepichka.service.implementation.TaskServiceImpl;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/tasks")
public class TaskController {

    private final TaskServiceImpl taskServiceImpl;
    private final EmployeeServiceImpl employeeServiceImpl;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<GetTaskDTO>> getAllTasks() {
        List<GetTaskDTO> tasks = taskServiceImpl.getAllTasks()
                .stream().map(this::convertToGetDTO)
                .toList();
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetTaskDTO> getTaskBuId(@PathVariable(name = "id") String id) {
        Optional<Task> task = taskServiceImpl.getTaskById(id);
        if (task.isPresent()) {
            GetTaskDTO taskResponse = convertToGetDTO(task.get());
            return new ResponseEntity<>(taskResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<GetTaskDTO> createTask(@Valid @RequestBody PostPutTaskDTO taskDTO) {
        try {
            Task taskRequest = convertPostPutDtoToEntity(taskDTO);
            Task task = taskServiceImpl.createTask(taskRequest);
            GetTaskDTO taskResponse = convertToGetDTO(task);
            return new ResponseEntity<>(taskResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<PostPutTaskDTO> replaceTask(@PathVariable(name = "id") String id,
                                                      @Valid @RequestBody PostPutTaskDTO taskDTO) {
        Task taskRequest = convertPostPutDtoToEntity(taskDTO);
        Task task = taskServiceImpl.updateTask(id, taskRequest);
        PostPutTaskDTO taskResponse = convertToPostPutDTO(task);
        return ResponseEntity.ok().body(taskResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteTask(@PathVariable(name = "id") String id) {
        try {
            taskServiceImpl.deleteTask(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private GetTaskDTO convertToGetDTO(Task task) {
        GetTaskDTO taskDTO = modelMapper.map(task, GetTaskDTO.class);
        taskDTO.convertDeadlineToString(task.getDeadline());
        taskDTO.convertStatusToString(task.getStatus());
        taskDTO.convertEmployeeToStringId(task.getEmployee());
        return taskDTO;
    }

    private PostPutTaskDTO convertToPostPutDTO(Task task){
        PostPutTaskDTO taskDTO = modelMapper.map(task, PostPutTaskDTO.class);
        taskDTO.convertDeadlineToString(task.getDeadline());
        taskDTO.convertStatusToString(task.getStatus());
        taskDTO.convertEmployeeToStringId(task.getEmployee());
        return taskDTO;
    }
    private Task convertPostPutDtoToEntity(PostPutTaskDTO taskDTO) {
        Task task = modelMapper.map(taskDTO, Task.class);
        task.setDeadline(taskDTO.convertStringToDeadline());
        task.setStatus(taskDTO.convertStringToStatus());

        Optional<Employee> employee = employeeServiceImpl.getEmployeeById(taskDTO.getEmployee());
        if (employee.isPresent()){
            task.setEmployee(employee.get());
            return task;
        } else {
            throw new BadIdException("No employee with id: "+taskDTO.getEmployee());
        }
    }

}
