package ru.internship.perepichka.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "employee")
public class Employee {

    @Id
    private Long id;

    private String name;

    @OneToMany(mappedBy = "employee",
            cascade = CascadeType.ALL)
    @JsonManagedReference
    @Fetch(FetchMode.JOIN)
    private List<Task> tasks;

    public Employee(long id, String name){
        this.id = id;
        this.name = name;
        this.tasks = new ArrayList<>();
    }

}