/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labo.entities;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;

/**
 *
 * @author NGS_004
 */
public class UserDTO {
    
    @NotNull
    @Size(min = 1, max = 50)
    private String login;

    @Size(min = 3, max = 50)
    @NotNull()
    private String firstName;

    @Size(min = 3, max = 50)
    @NotNull
    private String lastName;

    @Email
    @Size(min = 5, max = 100)
    private String email;

    private boolean activated = false;

    @Size(min = 2, max = 5)
    private String langKey;

    @NotNull
    @Size(min = 7, max = 20)
    private String tel;

   
    private Set<String> authorities;

    public UserDTO() {
    }

    public UserDTO(User user) {
        this(user.getUsername(), user.getFirstName(), user.getLastName(),
                user.getEmail(), user.getTel(), user.getActivated(), user.getLangKey(),
                user.getAuthorities());
    }

    public UserDTO(String login, String firstName, String lastName,
            String email, String tel, boolean activated, String langKey, Set<String> authorities) {

        this.login = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.tel = tel;
        this.activated = activated;
        this.langKey = langKey;
         
        this.authorities = authorities;
        
       
    }

    public String getLogin() {
        return login;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setActivated(boolean activated) {
        this.activated = activated;
    }

    public void setLangKey(String langKey) {
        this.langKey = langKey;
    }

    public void setAuthorities(Set<String> authorities) {
        this.authorities = authorities;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }

    public boolean isActivated() {
        return activated;
    }

    public String getLangKey() {
        return langKey;
    }

    public Set<String> getAuthorities() {
        if (authorities == null) {
            authorities = new HashSet<>();
        }
        return authorities;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    @Override
    public String toString() {
        return "UserDTO{"
                + "login='" + login + '\''
                + ", firstName='" + firstName + '\''
                + ", lastName='" + lastName + '\''
                + ", email='" + email + '\''
                + ", tel='" + tel + '\''
                + ", activated=" + activated
                + ", langKey='" + langKey + '\''
                + ", authorities=" + authorities
                + "}";
    }
}
