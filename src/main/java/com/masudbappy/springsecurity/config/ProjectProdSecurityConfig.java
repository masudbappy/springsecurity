package com.masudbappy.springsecurity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

@Configuration
@Profile("prod") // This configuration will only be active when the 'prod' profile is active
public class ProjectProdSecurityConfig {

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests((requests) -> {
                    requests.requestMatchers("/myAccount", "/myBalance",
                            "/myLoans", "/myCards").authenticated()
                            .requestMatchers("/notice", "/contact", "/error", "/register").permitAll();
        });
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());
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

    /*@Bean
    UserDetailsService userDetailsService() {
        UserDetails user = User.withUsername("user").password("{noop}user@123")
                .authorities("read").build();
        UserDetails admin = User.withUsername("admin")
                .password("{bcrypt}$2a$12$sz2AwV5vgKXeaZs0T8iM..kcOoUNbEP4cJGEprQ2yA7n8Csrz17Si")
                .authorities("read").build();
        return new InMemoryUserDetailsManager(user, admin);
    }*/

/*    @Bean
    UserDetailsService userDetailsService(DataSource dataSource) {
        return new JdbcUserDetailsManager(dataSource);
    }*/


    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }


    /*
    Introduced from spring-security 6
     */
    @Bean
    CompromisedPasswordChecker compromisedPasswordChecker() {
        return new HaveIBeenPwnedRestApiPasswordChecker();
    }
}
