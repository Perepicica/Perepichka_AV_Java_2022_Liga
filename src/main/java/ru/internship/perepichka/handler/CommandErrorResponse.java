package ru.internship.perepichka.handler;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CommandErrorResponse {
    private int status;
    private String message;
}
