/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labo.conf;


import com.labo.security.SecurityUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

/**
 *
 * @author kouwonou
 */
@Component
public class SpringSecurityAuditorAware implements AuditorAware<String> {
  
    @Override
    public String getCurrentAuditor() {
        return SecurityUtils.getCurrentUserLogin();
    }
 
}
