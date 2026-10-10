package com.nitin.EventSphere.config;

import com.nitin.EventSphere.filter.UserProvisioningFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.web.authentication.BearerTokenAuthenticationFilter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean // when we define bean , then spring automatically passes the parameters required to it , like in this case this requires userprivisionfilter which is our custom made , automatically passes due to @bean annotation
    public SecurityFilterChain filterChain(
            HttpSecurity http,
            UserProvisioningFilter userProvisioningFilter,
            JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception {

        http.authorizeHttpRequests(authorize ->
                authorize
                        .requestMatchers(HttpMethod.GET, "/api/v1/published-events/**").permitAll()
                        .requestMatchers("/api/v1/events").hasRole("ORGANIZER")
                        .requestMatchers("/api/v1/ticket-validations").hasRole("STAFF")
                        .anyRequest().authenticated()
        ); // authorize just an helper object which helps build the feature using its methods

        http.csrf(csrf -> csrf.disable()); // dont apply springs csrf protection as this is stateless no sessions

        http.sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS) // makes stateless meaning does not hold sessions all stateless request
        );

        http.oauth2ResourceServer(oauth2 ->
                oauth2.jwt(jwt ->
                        jwt.jwtAuthenticationConverter(jwtAuthenticationConverter) // this converter to configure how auth object is created using jwt to store in security context
                )
        ); // acts as jwt resource server , expecting jwt from things like keycloack , when we do this it gives us some inbuilt filters like bearertokenauthfilter etc , so extract validate jwt automatically or else we had to do all manually usd and all 

        http.addFilterAfter(userProvisioningFilter, BearerTokenAuthenticationFilter.class);

        return http.build();
    }
}
// done after creating userprivision filter , to add this filter in the filter chain this class is used