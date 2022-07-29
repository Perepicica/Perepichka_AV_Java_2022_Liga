package ru.internship.perepichka.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataForTaskUpdate {

    private String taskId;
    private String fieldName;
    private String newValue;

}
