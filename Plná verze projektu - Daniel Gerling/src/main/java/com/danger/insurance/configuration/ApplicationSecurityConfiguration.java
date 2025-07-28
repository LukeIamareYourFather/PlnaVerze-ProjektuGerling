package com.danger.insurance.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity(securedEnabled = true, jsr250Enabled = true)
public class ApplicationSecurityConfiguration {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )
            .formLogin(form -> form
                .loginPage("/accounts/log-in")
                .loginProcessingUrl("/accounts/login")
                .defaultSuccessUrl("/news", true)
                .usernameParameter("userEmail")
                .permitAll()
            )
            .logout(logout -> logout
            	.logoutUrl("/accounts/logout")
	            .logoutSuccessUrl("/about-us") // or any redirect page
	            .invalidateHttpSession(true)
	            .deleteCookies("JSESSIONID")
            );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
