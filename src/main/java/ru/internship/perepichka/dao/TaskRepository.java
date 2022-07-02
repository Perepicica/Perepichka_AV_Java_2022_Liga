package ru.internship.perepichka.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.internship.perepichka.entity.Task;

public interface TaskRepository extends JpaRepository<Task,Long> {
}
