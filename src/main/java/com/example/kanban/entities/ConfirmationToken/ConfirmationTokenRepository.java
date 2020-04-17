package com.example.kanban.entities.ConfirmationToken;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

public interface ConfirmationTokenRepository extends JpaRepository<ConfirmationToken, String> {
    ConfirmationToken findByConfirmationToken(String confirmationToken);

    @Transactional
    void deleteByConfirmationToken(String confirmationToken);
}
