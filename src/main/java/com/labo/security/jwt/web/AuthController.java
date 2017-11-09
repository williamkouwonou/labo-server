package com.labo.security.jwt.web;

import com.labo.entities.User;
import com.labo.repositories.UserRepository;
import com.labo.security.jwt.JwtAuthenticationRequest;
import com.labo.security.jwt.JwtAuthenticationResponse;
import com.labo.security.jwt.JwtUtil;
import com.labo.security.jwt.exception.InvalidPasswordException;
import com.labo.security.jwt.exception.UserNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.persistence.NoResultException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * AuthController provides signup, signin and token refresh methods
 *
 * @author saka7
 */
@RestController
public class AuthController {

    private static final Logger LOG = Logger.getLogger(AuthController.class.getName());

    @Value("${auth.header}")
    private String tokenHeader;

    public final static String SIGNUP_URL = "/api/auth/signup";
    public final static String LOGOUT_URL = "/admin/auth/logout";
    public final static String SIGNIN_URL = "/api/auth/signin";
    public final static String REFRESH_TOKEN_URL = "/api/auth/token/refresh";

    private AuthenticationManager authenticationManager;
    private JwtUtil jwtUtil;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * Injects AuthenticationManager instance
     *
     * @param authenticationManager to inject
     */
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Injects JwtUtil instance
     *
     * @param jwtUtil to inject
     */
    @Autowired
    public void setJwtTokenUtil(JwtUtil jwtUtil) {
        this.jwtUtil = jwtUtil;
    }

 

    @Bean
    private PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


    /**
     * Returns authentication token for given user
     *
     * @param authenticationRequest with username and password
     * @return generated JWT
     * @throws AuthenticationException
     */
    @PostMapping(SIGNIN_URL)
    public ResponseEntity getAuthenticationToken(@RequestBody JwtAuthenticationRequest authenticationRequest)
            throws AuthenticationException {

        final String name = authenticationRequest.getUsername();
        final String password = authenticationRequest.getPassword();
        LOG.log(Level.INFO, "[POST] GETTING TOKEN FOR User {0}", name);

        User u = null;
        try {
            u = userRepository.findByLogin(name);
        } catch (UsernameNotFoundException | NoResultException ex) {
            LOG.info(ex.getMessage());
            throw new UserNotFoundException();
        } catch (Exception ex) {
            LOG.info(ex.getMessage());
            return new ResponseEntity<>(ex, HttpStatus.BAD_REQUEST);
        }

        if (!passwordEncoder.matches(password, u.getPassword())) {
            throw new InvalidPasswordException();
        }

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(name, password)
        );

        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtUtil.generateToken(u);
        return ResponseEntity.ok(new JwtAuthenticationResponse(token));
    }

    /**
     * Refreshes token
     *
     * @param request with old JWT
     * @return Refreshed JWT
     */
    @PostMapping(REFRESH_TOKEN_URL)
    public ResponseEntity refreshAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(tokenHeader);
        LOG.info("[POST] REFRESHING TOKEN");
        String refreshedToken = jwtUtil.refreshToken(token);
        return ResponseEntity.ok(new JwtAuthenticationResponse(refreshedToken));
    }
    @PostMapping(LOGOUT_URL)
    public void logout(HttpServletRequest request,HttpServletResponse response) throws IOException {
        String token = request.getHeader(tokenHeader);
        LOG.info("[POST] REFRESHING TOKEN");
       response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        
        HttpSession session = request.getSession();
        
        System.out.println("hhh "+request.getAttribute("logout_url").toString());
        if (session == null) {
            response.sendRedirect(request.getAttribute("logout_url").toString());
            return;
        }
        session.removeAttribute("user");
        session.invalidate();
       response.sendRedirect(request.getAttribute("logout_url").toString());
    }

}
