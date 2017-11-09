package com.labo.security.jwt;


import com.labo.entities.User;
import com.labo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

/**
 * @author saka7 Checks JWT validity
 */
@Component
public class JwtAuthenticationTokenFilter extends OncePerRequestFilter {

    @Autowired
    private UserRepository userRepository;

    private JwtUtil jwtTokenUtil;

    @Value("${auth.header}")
    private String tokenHeader;

   

    /**
     * Injects JwtUtil instance
     *
     * @param jwtUtil to inject
     */
    @Autowired
    public void setJwtTokenUtil(JwtUtil jwtUtil) {
        this.jwtTokenUtil = jwtUtil;
    }

    /**
     * Checks if JWT present and valid
     *
     * @param request with JWT
     * @param response
     * @param chain
     * @throws ServletException
     * @throws IOException
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
            FilterChain chain) throws ServletException, IOException {

        String authToken = request.getHeader(this.tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(authToken);

        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            User userDetails = this.userRepository.findByLogin(username);
            if (jwtTokenUtil.validateToken(authToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication
                        = new UsernamePasswordAuthenticationToken(userDetails, null, toGrantedAuthorities(userDetails));
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                logger.info("Authenticated user " + username + ", setting security context");
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
      chain.doFilter(request, response);
    }

    private static Collection<GrantedAuthority> toGrantedAuthorities(final User user) {
        final Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        user.getAuthorities().stream().forEach((authority) -> {
            grantedAuthorities.add(new SimpleGrantedAuthority(authority));
        });
        return grantedAuthorities;
    }
}
