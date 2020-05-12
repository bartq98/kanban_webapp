package com.example.kanban.entities.task;

import com.example.kanban.entities.sections.Section;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface TaskRepository extends CrudRepository<Task, Integer> {

    @Query("SELECT s.tasks FROM Section s WHERE s.board.id = ?1")
    Optional<Task[]> tasksFromBoard (Integer BID);

    @Query("SELECT s.tasks FROM Section s WHERE s.id=?1")
    Optional<Task[]> tasksFromSection (Integer CID);

    @Modifying @Transactional
    @Query("UPDATE Task t SET t.section = :new_column WHERE t.id = :task_id")
    void updateOnMove(@Param("task_id") Integer task_id, @Param("new_column") Section new_column);

}
