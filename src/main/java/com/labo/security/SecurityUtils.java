package com.labo.security;


import com.labo.entities.User;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

/**
 * Utility class for Spring Security.
 */
public final class SecurityUtils {

    private SecurityUtils() {
    }

    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */

    
    public static String getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        String userName ="SYSTEM";
        
        if (authentication != null) {
            try {
                User u = (User) authentication.getPrincipal();
            userName = u.getUsername();
            } catch (Exception e) {
                userName=authentication.toString();
            }
            
        }

        return userName;
    }

    /**
     * Check if a user is authenticated.
     *
     * @return true if the user is authenticated, false otherwise
     */
    public static boolean isAuthenticated() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Collection<? extends GrantedAuthority> authorities = securityContext.getAuthentication().getAuthorities();
        if (authorities != null) {
            if (!authorities.stream().noneMatch((authority) -> (authority.getAuthority().equals(AuthoritiesConstants.ANONYMOUS)))) {
                return false;
            }
        }
        return true;
    }

    /**
     * If the current user has a specific authority (security role).
     *
     * <p>The name of this method comes from the isUserInRole() method in the Servlet API</p>
     *
     * @param authority the authority to check
     * @return true if the current user has the authority, false otherwise
     */
    public static boolean isCurrentUserInRole(String authority) {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        Authentication authentication = securityContext.getAuthentication();
        if (authentication != null) {
            if (authentication.getPrincipal() instanceof UserDetails) {
                UserDetails springSecurityUser = (UserDetails) authentication.getPrincipal();
                return springSecurityUser.getAuthorities().contains(new SimpleGrantedAuthority(authority));
            }
        }
        return false;
    }
}
