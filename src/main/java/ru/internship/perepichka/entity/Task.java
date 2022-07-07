package ru.internship.perepichka.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "task")
public class Task {

    @Id
    private Long id;

    private String header;

    private String description;

    @Temporal(TemporalType.DATE)
    private Date deadline;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonBackReference
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Override
    public String toString() {
        return  "id=" + id +
                ", header=" + header +
                ", description=" + description +
                ", deadline=" + deadline +
                ", employee=" + employee.getName() +
                ", status=" + status +"\n";
    }

    public enum Status {
        NEW, IN_PROGRESS, DONE
    }

}
