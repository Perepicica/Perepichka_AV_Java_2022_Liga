package ru.internship.perepichka.specification;

import org.springframework.data.jpa.domain.Specification;
import ru.internship.perepichka.dto.TaskFilters;
import ru.internship.perepichka.entity.Employee;
import ru.internship.perepichka.entity.Employee_;
import ru.internship.perepichka.entity.Task;
import ru.internship.perepichka.entity.Task_;

import javax.persistence.criteria.*;
import java.time.LocalDate;

public class EmployeeSpecification {

    private EmployeeSpecification(){}

    public static Specification<Employee> getEmployeesWithFilteredTasks(TaskFilters filters) {
        return (root, query, criteriaBuilder) -> {
            Join<Employee, Task> joinTable = root.join(Employee_.tasks);
            return criteriaBuilder.and(
                    filterTasksByStatus(filters.getStatus(), criteriaBuilder, joinTable),
                    filterTasksByDate(filters.getDeadline(), criteriaBuilder, joinTable)
            );
        };
    }

    public static Predicate filterTasksByStatus(
            Task.Status status, CriteriaBuilder criteriaBuilder, Join<Employee, Task> joinTable
    ) {
        if (status == null) {
            return criteriaBuilder.and();
        }
        return criteriaBuilder.equal(joinTable.get(Task_.STATUS), status);
    }

    public static Predicate filterTasksByDate(
            LocalDate date, CriteriaBuilder criteriaBuilder, Join<Employee, Task> joinTable
    ) {
        if (date == null) {
            return criteriaBuilder.and();
        }
        return criteriaBuilder.lessThanOrEqualTo(joinTable.get(Task_.DEADLINE), date);
    }
}
