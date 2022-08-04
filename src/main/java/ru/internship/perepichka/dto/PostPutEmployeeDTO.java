package ru.internship.perepichka.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;
import ru.internship.perepichka.entity.Employee;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

@Getter
@Setter
public class PostPutEmployeeDTO {

    @NotEmpty
    private String name;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    @Size(min = 8, message = "password should have at least 8 characters")
    private String password;

    @JsonIgnore
    public Employee getAsEmployee(){
        Employee result = new Employee();
        result.setName(name);
        result.setEmail(email);
        result.setPassword(password);
        return result;
    }
}
