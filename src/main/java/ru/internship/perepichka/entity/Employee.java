package ru.internship.perepichka.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;

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
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "varchar")
    private String id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "employee",
            cascade = CascadeType.ALL)
    @JsonManagedReference
    @Fetch(FetchMode.JOIN)
    private List<Task> tasks;

    public Employee(String name){
        this.name = name;
        this.tasks = new ArrayList<>();
    }

    public Employee(String id, String name){
        this.id = id;
        this.name = name;
        this.tasks = new ArrayList<>();
    }

}