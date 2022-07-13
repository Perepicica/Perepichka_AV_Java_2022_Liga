package ru.internship.perepichka.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "task")
public class Task {

    @Id
    @NonNull
    private Long id;

    @NonNull
    private String header;

    private String description;

    @Column(columnDefinition = "Date")
    private LocalDate deadline;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonBackReference
    private Employee employee;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private Status status = Status.NEW;

    @Override
    public String toString() {
        return "id=" + id +
                ", header=" + header +
                ", description=" + description +
                ", deadline=" + deadline +
                ", employeeID=" + employee.getId() +
                ", status=" + status + "\n";
    }

    public enum Status {
        NEW, IN_PROGRESS, DONE
    }

}
