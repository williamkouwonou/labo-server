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
    private String username;

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
 
    private String indicatif;
   
    private Set<String> authorities;
private String password;
    public UserDTO() {
    }

    public UserDTO(User user) {
        this(user.getUsername(), user.getFirstName(), user.getLastName(),
                user.getEmail(), user.getTel(),user.getIndicatif(), user.getActivated(), user.getLangKey(),
                user.getAuthorities());
    }

    public UserDTO(String login, String firstName, String lastName,
            String email, String tel,String indicatif, Boolean activated, String langKey, Set<String> authorities) {

        this.username = login;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.tel = tel;
        this.activated = activated;
        this.langKey = langKey;
         this.indicatif=indicatif;
        this.authorities = authorities;
        
       
    }

    public String getIndicatif() {
        return indicatif;
    }

    public void setIndicatif(String indicatif) {
        this.indicatif = indicatif;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    

    public String getFirstName() {
        return firstName;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserDTO{" + "username=" + username + ", firstName=" + firstName + ", lastName=" + lastName + ", email=" + email + ", activated=" + activated + ", langKey=" + langKey + ", tel=" + tel + ", indicatif=" + indicatif + ", authorities=" + authorities + ", password=" + password + '}';
    }

   
}
