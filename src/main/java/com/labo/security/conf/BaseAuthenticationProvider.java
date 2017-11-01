/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.labo.security.conf;

import com.labo.entities.User;
import com.labo.repositories.UserRepository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

/**
 *
 * @author sewa
 */
@Component
public class BaseAuthenticationProvider implements AuthenticationProvider {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HttpServletRequest request;

    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @Override

    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(null, null, null);

        String username = authentication.getName();
        String password = (String) authentication.getCredentials();

        User user = userRepository.findByLogin(username);

        if (user == null || !user.getUsername().equalsIgnoreCase(username)) {
            throw new BadCredentialsException("unknown user");
        }

        if (!encoder.matches(password, user.getPassword())) {
            throw new BadCredentialsException("unknown user");
        }

        if (!user.getActivated()) {
            throw new BadCredentialsException("unknown user");
        }

        usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(user, password, toGrantedAuthorities(user));
        return usernamePasswordAuthenticationToken;
    }

    private static Collection<GrantedAuthority> toGrantedAuthorities(final User user) {
        final Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        user.getAuthorities().stream().forEach((authority) -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        });
        return grantedAuthorities;
    }

    private Map<String, String> getHeadersInfo() {

        Map<String, String> map = new HashMap<>();
        Enumeration headerNames = request.getHeaderNames();
        while (headerNames.hasMoreElements()) {
            String key = (String) headerNames.nextElement();
            String value = request.getHeader(key);
            map.put(key, value);
        }
        return map;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
    private final org.slf4j.Logger log = LoggerFactory.getLogger(BaseAuthenticationProvider.class);

}
