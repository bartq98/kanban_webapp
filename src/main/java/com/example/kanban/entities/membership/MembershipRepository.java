package com.example.kanban.entities.membership;

import com.example.kanban.entities.boards.Board;
import com.example.kanban.entities.user.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipRepository extends CrudRepository<Membership, Integer> {

    Optional<Membership> findById(Integer id);
    List<Membership> findByUser(Integer id);

    @Query(value = "SELECT * FROM membership WHERE board_id = ?1 AND user_id = ?2", nativeQuery = true)
    Optional<Membership> getMembershipsByBoardIdAndUserId(Integer boardId, Integer userId);

    boolean existsByUserAndBoard(User user, Board board);
}
