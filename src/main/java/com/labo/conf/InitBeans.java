/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labo.conf;

import com.labo.entities.Group;
import com.labo.entities.Permission;
import com.labo.entities.User;
import com.labo.entities.UserDTO;
import com.labo.repositories.GroupRepository;
import com.labo.repositories.PermissionRepository;
import com.labo.repositories.UserRepository;
import com.labo.security.AuthoritiesConstants;
import com.labo.service.UserService;
import javax.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 *
 * @author NGS_004
 */
@Configuration
public class InitBeans {

    @Autowired
    private UserService userService;
    @Autowired
    private PermissionRepository permissionRepository;
    @Autowired
    private GroupRepository groupRepository;
    @Autowired
    private UserRepository userRepository;

    @PostConstruct
    public void init() {
        initPermission();
        initGroup();
        initAdminUser();
    }

    public void initPermission() {

        if (permissionRepository.findByName(AuthoritiesConstants.ADMIN).isEmpty()) {
            Permission p = new Permission(AuthoritiesConstants.ADMIN, "Acces à tout le system");
            permissionRepository.save(p);

        }
        if (permissionRepository.findByName(AuthoritiesConstants.USER).isEmpty()) {
            Permission p = new Permission(AuthoritiesConstants.USER, "Acces limité");
            permissionRepository.save(p);

        }
        if (permissionRepository.findByName(AuthoritiesConstants.ANONYMOUS).isEmpty()) {
            Permission p = new Permission(AuthoritiesConstants.ADMIN, "Aucun acces");
            permissionRepository.save(p);

        }
    }

    public void initGroup() {

        if (groupRepository.findByName("Administrateur").isEmpty()) {
            Group p = new Group("Administrateur", "Acces à tout le system");
            p.getPermissions().addAll(permissionRepository.findAll());
            groupRepository.save(p);

        }
        if (groupRepository.findByName("Utilisateur").isEmpty()) {
            Group p = new Group("Utilisateur", "acces limité");
            p.getPermissions().addAll(permissionRepository.findByName(AuthoritiesConstants.USER));
            groupRepository.save(p);

        }
        if (groupRepository.findByName("Anonyme").isEmpty()) {
            Group p = new Group("Anonyme", "Aucun acces");
            p.getPermissions().addAll(permissionRepository.findByName(AuthoritiesConstants.ANONYMOUS));
            groupRepository.save(p);

        }

    }

    public void initAdminUser() {
        if (userRepository.findByLogin("admin") == null) {
            User u = new User();
            u.setUsername("admin");
            u.setFirstName("Admin");
            u.setLastName("labo");
            u.setTel("98858651");
            u.setIndicatif("228");
            u.setEmail("admin@gmail.com");
            u.setPassword("admin");
            u.setActivated(Boolean.TRUE);
            u.setGroup(groupRepository.findByName("Administrateur").get(0));
            UserDTO us = new UserDTO(u);
            us.setPassword(u.getPassword());
            userService.createUser(us, u.getGroup());
        }
    }

    @Bean
    public PasswordEncoder initPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
