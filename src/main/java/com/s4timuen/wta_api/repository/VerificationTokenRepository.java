package com.s4timuen.wta_api.repository;

import com.s4timuen.wta_api.entity.VerificationToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * Repository for verification tokens.
 */
@Repository
public interface VerificationTokenRepository extends JpaRepository<VerificationToken, Long> {
}
