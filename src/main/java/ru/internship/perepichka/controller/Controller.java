package ru.internship.perepichka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import ru.internship.perepichka.dto.DataForControllerProcessing;
import ru.internship.perepichka.dto.TaskFilters;
import ru.internship.perepichka.service.EmployeeServiceFacade;
import ru.internship.perepichka.service.TaskServiceFacade;
import ru.internship.perepichka.util.CommandParser;
import ru.internship.perepichka.util.DataParser;

import java.util.Optional;


@RestController
@RequiredArgsConstructor
@RequestMapping("api")
public class Controller {

    private final EmployeeServiceFacade employeeServiceFacade;
    private final TaskServiceFacade taskServiceFacade;


    @GetMapping("/{command}")
    public String processCommand(@PathVariable String command) {
        DataForControllerProcessing dataForProc = CommandParser.parseControllerCommand(command);

        if (dataForProc.getClassName() == EmployeeServiceFacade.class) {
            return employeeServiceFacade.process(dataForProc.getCommand(), dataForProc.getArgs());
        } else {
            return taskServiceFacade.process(dataForProc.getCommand(), dataForProc.getArgs());
        }
    }

    @GetMapping("/maxTasksEmployee")
    public String getEmployeeWithMaxTasks(@RequestParam(value = "status") Optional<String> status,
                                          @RequestParam(value = "deadline") Optional<String> deadline) {
        return employeeServiceFacade.getEmployeeWithMaxTasks(
                TaskFilters.builder()
                        .status(status.map(DataParser::getStatusType).orElse(null))
                        .deadline(deadline.map(DataParser::getDeadLine).orElse(null))
                        .build()
        );
    }

}
