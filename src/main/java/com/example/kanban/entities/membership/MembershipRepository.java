package com.example.kanban.entities.membership;

import com.example.kanban.entities.boards.Board;
import com.example.kanban.entities.user.User;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipRepository extends CrudRepository<Membership, Integer> {

    Optional<Membership> findById(Integer id);
    List<Membership> findByUser(Integer id);

    boolean existsByUserAndBoard(User user, Board board);
}
