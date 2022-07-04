package com.s4timuen.wta_api.repository;

import com.s4timuen.wta_api.entity.PasswordChangeToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for password change tokens.
 */
@Repository
public interface PasswordChangeTokenRepository extends JpaRepository<PasswordChangeToken, Long> {

    /**
     * Query repository for the respective password change token.
     *
     * @return Respective password reset token.
     */
    Optional<PasswordChangeToken> findByToken(String token);
}
