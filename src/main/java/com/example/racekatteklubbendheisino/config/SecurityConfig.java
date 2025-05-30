package com.example.racekatteklubbendheisino.config;

import com.example.racekatteklubbendheisino.application.MemberUserDetailsService;
import com.example.racekatteklubbendheisino.infrastructure.JdbcMemberRepository;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final MemberUserDetailsService memberUserDetailsService;
    private final JdbcMemberRepository memberRepository;

    public SecurityConfig(MemberUserDetailsService memberUserDetailsService, JdbcMemberRepository memberRepository) {
        this.memberUserDetailsService = memberUserDetailsService;
        this.memberRepository = memberRepository;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(memberUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());

        http
                .authenticationProvider(authProvider)
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/admin/**").hasRole("ADMIN")
                        .requestMatchers("/member/**").hasRole("MEMBER")
                        .requestMatchers("/pets/**", "/dashboard").authenticated()
                        .requestMatchers("/login", "/register", "/css/**").permitAll()
                        .anyRequest().permitAll()
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .successHandler(customAuthenticationSuccessHandler())
                        .failureUrl("/login?error")
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutSuccessUrl("/login?logout")
                        .permitAll()
                );
        return http.build();
    }

    // Håndterer hvad der sker efter login
    @Bean
    public AuthenticationSuccessHandler customAuthenticationSuccessHandler() {
        return (request, response, authentication) -> {

            String email = authentication.getName();
            memberRepository.updateLastLogin(email);


            if (authentication.getAuthorities().stream()
                    .anyMatch(grantedAuthority ->
                            grantedAuthority.getAuthority().equals("ROLE_ADMIN"))) {
                response.sendRedirect("/admin");
            } else {
                response.sendRedirect("/pets");
            }
        };
    }

    // Krypterer adgangskoder
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}