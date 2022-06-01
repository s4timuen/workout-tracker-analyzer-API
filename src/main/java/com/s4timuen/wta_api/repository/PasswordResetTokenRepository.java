package com.s4timuen.wta_api.repository;

import com.s4timuen.wta_api.entity.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for password reset tokens.
 */
@Repository
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {

    /**
     * Query repository for the respective password reset token.
     *
     * @return Respective password reset token.
     */
    Optional<PasswordResetToken> findByToken(String token);
}
