package ru.internship.perepichka.controller;

import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.internship.perepichka.dto.TaskDTO;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.service.implementation.TaskServiceImpl;

import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/tasks")
public class TaskController {

    private final TaskServiceImpl taskServiceImpl;
    private final ModelMapper modelMapper;

    @GetMapping
    public ResponseEntity<List<TaskDTO>> getAllTasks() {
        List<TaskDTO> tasks = taskServiceImpl.getAllTasks()
                .stream().map(this::convertToDTO)
                .toList();
        if (tasks.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(tasks, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> getTaskBuId(@PathVariable(name = "id") String id) {
        Optional<Task> task = taskServiceImpl.getTaskById(id);
        if (task.isPresent()) {
            TaskDTO taskResponse = convertToDTO(task.get());
            return new ResponseEntity<>(taskResponse, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<TaskDTO> createTask(@RequestBody TaskDTO taskDTO) {
        try {
            Task taskRequest = convertToEntity(taskDTO);
            Task task = taskServiceImpl.createTask(taskRequest);
            TaskDTO taskResponse = convertToDTO(task);
            return new ResponseEntity<>(taskResponse, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> replaceTask(@RequestBody TaskDTO taskDTO, @PathVariable(name = "id") String id) {
        Task taskRequest = convertToEntity(taskDTO);
        Task task = taskServiceImpl.updateTask(id, taskRequest);
        TaskDTO taskResponse = convertToDTO(task);
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

    private TaskDTO convertToDTO(Task task) {
        TaskDTO taskDTO = modelMapper.map(task, TaskDTO.class);
        taskDTO.convertDeadlineToString(task.getDeadline());
        taskDTO.convertStatusToString(task.getStatus());
        return taskDTO;
    }

    private Task convertToEntity(TaskDTO taskDTO) {
        Task task = modelMapper.map(taskDTO, Task.class);
        task.setDeadline(taskDTO.convertStringToDeadline());
        task.setStatus(taskDTO.convertStringToStatus());
        return task;
    }

}
