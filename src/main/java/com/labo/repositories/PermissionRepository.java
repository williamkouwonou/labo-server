/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labo.repositories;

import com.labo.entities.Permission;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 *
 * @author NGS_004
 */
public interface PermissionRepository  extends JpaRepository<Permission, String>{
    @Query("SELECT p FROM Permission p WHERE p.name=:n")
    public List<Permission> findByName(@Param("n")String name);
}
