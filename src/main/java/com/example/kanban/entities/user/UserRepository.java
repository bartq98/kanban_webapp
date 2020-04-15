package com.example.kanban.entities.user;

import com.example.kanban.entities.membership.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT u.memberships FROM User u")
    List<Membership> getAllMemberships(Integer id);

    Optional<User> findById(Integer id);
    Optional<User> findByEmail(String email);

    boolean existsByUserName(String userName);
    boolean existsByEmail(String email);
}
