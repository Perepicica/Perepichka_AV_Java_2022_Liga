package ru.internship.perepichka.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.internship.perepichka.service.ServiceFacade;

@Getter
@Setter
@NoArgsConstructor
public class DataForControllerProcessing {
    private Class<? extends ServiceFacade> className;
    private String command;
    private String args;
}
