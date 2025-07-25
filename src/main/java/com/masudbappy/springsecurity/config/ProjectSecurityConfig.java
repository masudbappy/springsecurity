package com.masudbappy.springsecurity.config;

import com.masudbappy.springsecurity.events.CustomAuthenticationFailureHandler;
import com.masudbappy.springsecurity.events.CustomAuthenticationSuccessHandler;
import com.masudbappy.springsecurity.exceptions.CustomAccessDeniedHandler;
import com.masudbappy.springsecurity.exceptions.CustomBasicAuthenticationEntryPoint;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.authentication.password.CompromisedPasswordChecker;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.password.HaveIBeenPwnedRestApiPasswordChecker;

@Configuration
@Profile("!prod") // This configuration will be active when the 'prod' profile is not active
public class ProjectSecurityConfig {

    private final CustomAuthenticationSuccessHandler authenticationSuccessHandler;
    private final CustomAuthenticationFailureHandler authenticationFailureHandler;

    public ProjectSecurityConfig(CustomAuthenticationSuccessHandler authenticationSuccessHandler, CustomAuthenticationFailureHandler authenticationFailureHandler) {
        this.authenticationSuccessHandler = authenticationSuccessHandler;
        this.authenticationFailureHandler = authenticationFailureHandler;
    }

    @Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {

        http.csrf((csrf) -> csrf.disable())
                .authorizeHttpRequests((requests) -> requests.requestMatchers("/dashboard").authenticated()
                        .requestMatchers("/", "/home", "/holidays/**", "/contact", "/saveMsg",
                                "/courses", "/about", "/assets/**", "/login/**").permitAll())
                .formLogin(flc -> flc.loginPage("/login").usernameParameter("userid").passwordParameter("secretPwd")
                        .defaultSuccessUrl("/dashboard").failureUrl("/login?error=true")
                        .successHandler(authenticationSuccessHandler).failureHandler(authenticationFailureHandler))
                .logout(loc -> loc.logoutSuccessUrl("/login?logout=true").invalidateHttpSession(true).clearAuthentication(true)
                        .deleteCookies("JSESSIONID"))
                .httpBasic(Customizer.withDefaults());


        return http.build();
    }

    /*@Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.requiresChannel(rcc -> rcc.anyRequest().requiresInsecure()) // Only HTTP requests
                .authorizeHttpRequests((requests) -> {
                    requests.requestMatchers("/myAccount", "/myBalance",
                            "/myLoans", "/myCards").authenticated()
                            .requestMatchers("/notice", "/contact", "/error", "/register").permitAll();
        });
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(Customizer.withDefaults());
        http.csrf(csrf -> csrf.disable());
        return http.build();
    }*/

    /*
    This method is includes custom-basic authentication entry point
     */
    /*@Bean
    SecurityFilterChain defaultSecurityFilterChain(HttpSecurity http) throws Exception {
        http.sessionManagement(smc->smc.invalidSessionUrl("/invalidSession")
                        .maximumSessions(3).maxSessionsPreventsLogin(true).expiredUrl("/expiredSession"))
        .requiresChannel(rcc -> rcc.anyRequest().requiresInsecure()) // Only HTTP requests
                .authorizeHttpRequests((requests) -> {
                    requests.requestMatchers("/myAccount", "/myBalance",
                                    "/myLoans", "/myCards").authenticated()
                            .requestMatchers("/notice", "/contact", "/error",
                                    "/register", "/invalidSession", "/expiredSession").permitAll();
                });
        http.formLogin(Customizer.withDefaults());
        http.httpBasic(hbc->hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint()));
//        http.exceptionHandling(hbc->hbc.authenticationEntryPoint(new CustomBasicAuthenticationEntryPoint())); // this is global config
        http.exceptionHandling(ehc -> ehc.accessDeniedHandler(new CustomAccessDeniedHandler()));
        http.csrf(AbstractHttpConfigurer::disable);
        return http.build();
    }*/
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
