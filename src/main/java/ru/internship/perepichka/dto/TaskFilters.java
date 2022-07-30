package ru.internship.perepichka.dto;

import lombok.Builder;
import lombok.Getter;
import ru.internship.perepichka.entity.Task;

import java.time.LocalDate;

@Builder
@Getter
public class TaskFilters {
    Task.Status status;
    LocalDate deadline;
}
