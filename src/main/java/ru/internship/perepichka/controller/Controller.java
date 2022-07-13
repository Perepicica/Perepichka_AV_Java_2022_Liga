package ru.internship.perepichka.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import ru.internship.perepichka.dto.DataForControllerProcessing;
import ru.internship.perepichka.service.EmployeeServiceFacade;
import ru.internship.perepichka.service.TaskServiceFacade;
import ru.internship.perepichka.util.CommandParser;


@RestController
@RequiredArgsConstructor
public class Controller {

    private final EmployeeServiceFacade employeeServiceFacade;
    private final TaskServiceFacade taskServiceFacade;


    @GetMapping("api/{command}")
    public String processCommand(@PathVariable String command) {
        DataForControllerProcessing dataForProc = CommandParser.parseControllerCommand(command);

        if (dataForProc.getClassName() == EmployeeServiceFacade.class) {
            return employeeServiceFacade.process(dataForProc.getCommand(), dataForProc.getArgs());
        } else {
            return taskServiceFacade.process(dataForProc.getCommand(), dataForProc.getArgs());
        }
    }
}
