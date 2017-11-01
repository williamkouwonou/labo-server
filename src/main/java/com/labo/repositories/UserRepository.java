/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labo.repositories;

import com.labo.entities.User;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author NGS_004
 */
public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findOneByActivationKey(String activationKey);

    Optional<User> findOneByResetKey(String resetKey);

    Optional<User> findOneByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.username=:u")
    Optional<User> findOneByLogin(@Param("u") String login);

    Optional<User> findOneById(String userId);
    
    @Query("SELECT u FROM User u WHERE u.username=:u")
    User findByLogin(@Param("u")String login);

    @Query("SELECT u FROM User u WHERE u.group.id=:id")
    Page<User> findAllByGroup(@Param("id") Long id, Pageable pageable);

    @Query("SELECT u FROM User u WHERE u.group.id=:id")
    List<User> findAllByGroup(@Param("id") String id);

    @Override
    void delete(User t);
}
