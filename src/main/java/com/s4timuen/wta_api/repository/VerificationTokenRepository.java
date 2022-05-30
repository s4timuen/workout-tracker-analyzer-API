package com.s4timuen.wta_api.repository;

import com.s4timuen.wta_api.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for verification tokens.
 */
@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {

    /**
     * Query repository for the respective registration verification token.
     *
     * @return Respective verification token.
     */
    Optional<VerificationToken> findByToken(String token);
}
