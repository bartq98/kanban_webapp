package com.example.kanban.entities.membership;

import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface MembershipRepository extends CrudRepository<Membership, Integer> {

    Optional<Membership> findById(Integer id);
    List<Membership> findByUser(Integer id);
}
