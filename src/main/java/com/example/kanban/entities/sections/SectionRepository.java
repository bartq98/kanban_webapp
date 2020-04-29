package com.example.kanban.entities.sections;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface SectionRepository extends CrudRepository<Section, Integer> {
    @Query("Select s FROM Section s WHERE s.board.id=?1")
    Optional<Section[]> getSectionsFromBoard(Integer BID);
}
