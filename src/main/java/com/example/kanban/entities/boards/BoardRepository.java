package com.example.kanban.entities.boards;

import com.example.kanban.entities.membership.Membership;
import com.example.kanban.entities.sections.Section;
import com.example.kanban.entities.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface BoardRepository extends CrudRepository<Board, Integer> {
//    @Query("SELECT u.memberships FROM User u")
//    List<Membership> getAllMemberships(Integer id);

    @Query("SELECT m.board FROM Membership m WHERE m.user.id = :logged_user")
    Optional<Board[]> getAllBoards(@Param("logged_user") Integer id);

    Optional<Board> findById(Integer id);
}
