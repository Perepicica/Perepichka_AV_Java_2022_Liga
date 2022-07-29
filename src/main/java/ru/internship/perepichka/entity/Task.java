package ru.internship.perepichka.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@NoArgsConstructor
@Getter
@Setter
@Table(name = "task")
public class Task {

    public Task(String header, String description, LocalDate deadline, Employee employee, Status status) {
        this.header = header;
        this.description = description;
        this.deadline = deadline;
        this.employee = employee;
        this.status = status;
    }

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "varchar")
    private String id;

    private String header;

    private String description;

    @Column(columnDefinition = "Date")
    private LocalDate deadline;

    @ManyToOne(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinColumn(name = "employee_id", nullable = false)
    @JsonBackReference
    private Employee employee;

    @Enumerated(EnumType.STRING)
    private Status status;

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
