/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labo.entities;

import com.labo.utils.RandomUtil;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

/**
 *
 * @author NGS_004
 */
@Entity
@Table(name = "labo_groups")
public class Group implements Serializable{
    @Id
    private String id;
    private String name;
    private String description;
    @ManyToMany
    @JoinTable(
            name = "labo_permissions",
            joinColumns = {
                @JoinColumn(name = "user_id", referencedColumnName = "id")},
            inverseJoinColumns = {
                @JoinColumn(name = "permission_name", referencedColumnName = "name")})
    @LazyCollection(LazyCollectionOption.FALSE)
    private Set<Permission> permissions = new HashSet<>();
    
    public Group() {
    }

    public Group(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return name;
    }

    public void setNom(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Group{" + "id=" + id + ", name=" + name + ", description=" + description + '}';
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 89 * hash + Objects.hashCode(this.id);
        hash = 89 * hash + Objects.hashCode(this.name);
        hash = 89 * hash + Objects.hashCode(this.description);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Group other = (Group) obj;
        if (!Objects.equals(this.id, other.id)) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        return true;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Permission> getPermissions() {
        return permissions;
    }

    public void setPermissions(Set<Permission> permissions) {
        this.permissions = permissions;
    }
    
    @PrePersist
    public void init() {
        setId(RandomUtil.generateResetKey());
    }
}
