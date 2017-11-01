/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labo.web.rest;

import com.labo.entities.Group;
import com.labo.entities.User;
import com.labo.entities.UserDTO;
import com.labo.repositories.GroupRepository;
import com.labo.repositories.UserRepository;
import com.labo.security.SecurityUtils;
import com.labo.service.UserService;
import com.labo.web.rest.utils.Utils;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author NGS_004
 */
@RestController
@RequestMapping("/api")
public class UserResource {

    private final Logger log = LoggerFactory.getLogger(UserResource.class);
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private GroupRepository groupRepository;

    @RequestMapping(value = "/users/create/{group}",
            method = RequestMethod.POST,
            produces = MediaType.APPLICATION_JSON_VALUE)

    public Object createUser(@RequestBody @Valid UserDTO managedUserVM,
            BindingResult bindingResult, @PathVariable("group") String g) throws URISyntaxException {

        log.info("REST request to save User : {}", managedUserVM);
        User u = userRepository.findByLogin(SecurityUtils.getCurrentUserLogin());

        Map<Object, Object> modele = new HashMap<>();
        modele.put(Utils.CODE, Utils.VALIDATION_ERROR_CODE);
        modele.put(Utils.CUSTOMERROR, "Erreur de validation");
        modele.put(Utils.ERROR, Boolean.TRUE);

        Boolean error = false;

        for (FieldError f : bindingResult.getFieldErrors()) {

            modele.put(f.getField(), f.getDefaultMessage());
            error = true;

        }
        log.info("REST request to save User 1: {}", managedUserVM);
        if (userRepository.findOneByLogin(managedUserVM.getUsername().toLowerCase()).isPresent()) {
            modele.put("login", "Ce login exite deja");
            error = true;
        }
        log.info("REST request to save User 2: {}", managedUserVM);
        if (userRepository.findOneByEmail(managedUserVM.getEmail()).isPresent()) {
            modele.put("email", "Cet email exite deja");
            error = true;
        }
        log.info("REST request to save User 3: {}", managedUserVM);
        Group group = groupRepository.findOne(g);
        if (group == null) {
            modele.put("group", "le group est obligatoire");
            error = true;
        }
        if (error) {
            return modele;
        }
        User newUser = userService.createUser(managedUserVM, group);
        //String baseUrl = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();              // "/myContextPath" or "" if deployed in root context
        log.info("REST request to save User 4: {}", managedUserVM);
        //mailService.sendCreationEmail(newUser, baseUrl);
        modele.put(Utils.CODE, Utils.OPERATION_SUCCESS_CODE);
        modele.put(Utils.CUSTOMERROR, "Enregistrement réeussi");
        modele.put(Utils.ERROR, Boolean.FALSE);
        return modele;

    }

}
