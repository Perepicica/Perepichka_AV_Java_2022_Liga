package ru.internship.perepichka.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.internship.perepichka.entity.Task;

@Repository
public interface TaskRepository extends JpaRepository<Task, String> {
}
