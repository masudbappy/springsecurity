package com.masudbappy.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class ProjectSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> {
                    requests.requestMatchers("/myAccount", "/myBalance",
                            "/myLoans", "/myCards").authenticated()
                            .requestMatchers("/notice", "/contact", "/error").permitAll();
        });
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        return http.build();
    }
    /*
    If you want to disable formLogin,
    This case is useful when your backend apis
    are consumed by any other services;
     */
    /*@Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> {
            requests.requestMatchers("/myAccount", "/myBalance",
                            "/myLoans", "/myCards").authenticated()
                    .requestMatchers("/notice", "/contact", "/error").permitAll();
        });
        http.formLogin(httpSecurityFormLoginConfigurer ->
                httpSecurityFormLoginConfigurer.disable());
        http.httpBasic(httpSecurityHttpBasicConfigurer ->
                httpSecurityHttpBasicConfigurer.disable());
        return http.build();
    }*/

    @Bean
    UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user").password("{noop}user")
                .authorities("read").build();
        UserDetails admin = User.withUsername("admin").password("{bcrypt}$2a$12$xXJzY9ywHcwotyyRiIBdz.atJ6EWDH4a0X8Hsef4wWApyZFCqBXRS")
                .authorities("read").build();
        return new InMemoryUserDetailsManager(user, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }
}
