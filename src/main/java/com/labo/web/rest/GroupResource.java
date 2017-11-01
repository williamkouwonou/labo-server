/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labo.web.rest;

import com.labo.entities.Group;
import com.labo.repositories.GroupRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author NGS_004
 */
@RestController
@RequestMapping("/api/groups")
public class GroupResource {
    @Autowired private GroupRepository groupRepository;
    @RequestMapping("/all")
    public Object findAll(){
        List<Group> groups=groupRepository.findAll();
       return groups;
    }
}
