package com.openclassrooms.paymybuddy.configuration;

import com.openclassrooms.paymybuddy.controler.OAuth2SuccessHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfiguration {
    @Autowired
    private OAuth2SuccessHandler oAuth2SuccessHandler;

    @Bean
    public static PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf().disable()
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/oauth2/**").permitAll()
                        .requestMatchers("/registration/**").permitAll()
                        .requestMatchers("/login/**").permitAll()
                        .requestMatchers("/user/**").hasAnyRole("USER", "ADMIN")
                        .requestMatchers("/admin/**").hasAnyRole("ADMIN")
                        .anyRequest().authenticated()
                )
                .formLogin((form) -> form
                        .loginPage("/login")
                        .loginProcessingUrl("/login")
                        .defaultSuccessUrl("/home")
                        .permitAll()
                )
                .oauth2Login(oauth2Login ->
                        oauth2Login
                                .loginPage("/login")
                                .successHandler(oAuth2SuccessHandler)
                                .defaultSuccessUrl("/home")
                                .permitAll()
                )
                .exceptionHandling().accessDeniedPage("/access-denied")
                .and()
                .anonymous().disable();
        return http.build();
    }

    public static String getEmailFromAuthentication(Authentication authentication) {
        Object principal = null;
        String email = null;

        if (authentication != null) {
            principal = authentication.getPrincipal();

            if (principal instanceof OAuth2User) {
                OAuth2User oAuth2User = (OAuth2User) principal;
                email = oAuth2User.getAttribute("email");
            } else if (principal instanceof UserDetails) {
                email = ((UserDetails) principal).getUsername();
            } else {
                email = principal.toString();
            }
        }
        return email;
    }
}