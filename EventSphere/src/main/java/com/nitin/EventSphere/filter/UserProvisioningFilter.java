package com.nitin.EventSphere.filter;

import com.nitin.EventSphere.domain.entities.User;
import com.nitin.EventSphere.repositories.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

@Component
@RequiredArgsConstructor
public class UserProvisioningFilter extends OncePerRequestFilter {

    private final UserRepository userRepository;

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication != null
                && authentication.isAuthenticated()
                && authentication.getPrincipal() instanceof Jwt jwt) {

            UUID keycloakId = UUID.fromString(jwt.getSubject());

            if (!userRepository.existsById(keycloakId)) {

                User user = new User();
                user.setId(keycloakId);
                user.setName(jwt.getClaimAsString("preferred_username"));
                user.setEmail(jwt.getClaimAsString("email"));

                userRepository.save(user);
            }

        }

        filterChain.doFilter(request, response);
    }
}
// security context holds autheuccation object for each request containing principla that can be jwt or username etc and is autehticatted or not all those things so we extract jwt from there and save the user , by extracting his details from claims , so first login we store data of user rather than using singup in usual auth implementation