package ru.internship.perepichka.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataForTaskUpdate {

    private long taskId;
    private String fieldName;
    private String newValue;

}
