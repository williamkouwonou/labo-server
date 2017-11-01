package com.labo.security;


import com.labo.entities.User;
import com.labo.entities.UserDTO;
import com.labo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Spring Security success handler, specialized for Ajax requests.
 */
@Component
public class CustomRestAuthenticationSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Autowired
    private UserRepository userRepository;
    

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication)
            throws ServletException, IOException {
        if (authentication != null) {
            User u1 = (User) authentication.getPrincipal();
            User user = userRepository.findByLogin(u1.getUsername());
            UserDTO u=null;
        if(user!=null){
            System.out.println("   FFFF"+user);
            u=new UserDTO(user);
        }
            System.out.println("User found " + user);
            CustomSecurityUtils.sendResponse(response, HttpServletResponse.SC_OK, u);
        }
    }
}
