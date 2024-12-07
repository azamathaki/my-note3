package com.portfolio.my_note.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.authentication.AuthenticationProvider;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(12);
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
        .csrf(customizer -> customizer.disable())
        .authorizeHttpRequests(request -> request
            .requestMatchers("/login", "/signup", "/checkingSignup", "/").permitAll() // Allow unauthenticated access to login and signup pages
            .anyRequest().authenticated()) // Protect all other URLs
        .formLogin(form -> form
            .loginPage("/login") // Custom login page URL
            .loginProcessingUrl("/checkingLogin") // Custom login form submission URL
            .defaultSuccessUrl("/home", true) // Custom success handler
            .failureUrl("/login?error=invalidCredentials")
            .permitAll() // Allow unauthenticated access to the login page
        ).logout(logout -> logout
        .logoutUrl("/logout") // Define the logout URL
        .logoutSuccessUrl("/login") // Redirect to login page after logout
        .invalidateHttpSession(true) // Invalidate the HTTP session
        .clearAuthentication(true)) // Clear authentication information
        .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        //provider.setPasswordEncoder(NoOpPasswordEncoder.getInstance()); //this line for plain text password
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

}
