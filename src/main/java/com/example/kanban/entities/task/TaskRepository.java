package com.example.kanban.entities.task;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Integer> {

    @Query("SELECT s.tasks FROM Section s WHERE s.board.id = ?1")
    Optional<Task[]> tasksFromBoard (Integer BID);

    @Query("SELECT s.tasks FROM Section s WHERE s.id=?1")
    Optional<Task[]> tasksFromSection (Integer CID);
}
