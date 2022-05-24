package com.s4timuen.wta_api.repository;

import com.s4timuen.wta_api.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * Repository for users.
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Query repository for the respective user by a nickname.
     *
     * @return User data for the respective nickname, from repository.
     */
    Optional<User> findUserByNickname(String nickname);

    /**
     * Query repository for the respective user by an email address.
     *
     * @return User data for the respective email address, from repository.
     */
    Optional<User> findUserByEmail(String email);
}
