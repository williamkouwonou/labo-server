package com.labo.security.jwt;


import com.labo.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;

public final class JwtUserFactory {

    private JwtUserFactory() {}

    public static JwtUser create(User user) {
        return new JwtUser(
                user.getId(),
                user.getFirstName(),
                user.getEmail(),
                user.getPassword(),
                toGrantedAuthorities(user)
        );
    }

    private static Collection<GrantedAuthority> toGrantedAuthorities(final User user) {
        final Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        user.getAuthorities().stream().forEach((authority) -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        });
        return grantedAuthorities;
    }

}
