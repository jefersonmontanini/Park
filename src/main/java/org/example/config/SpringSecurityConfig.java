package org.example.config;

import org.example.JWT.JwtAuthenticationEntryPoint;
import org.example.JWT.JwtAuthorizationFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.servlet.util.matcher.MvcRequestMatcher;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableWebMvc
@Configuration
public class SpringSecurityConfig {

//    private static final String[] DOCUMENTATION_OPENAPI = {
//            "docs/index.html",
//            "docs-park.html", "docs-park/**",
//            "/v3/api-docs/**",
//            "/swagger-ui-custom.html", "swagger-ui.html", "/swagger-ui/**",
//            "/**.html", "webjars/**", "configurations/**", "swagger-resources/**"
//    };
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .formLogin(form -> form.disable())
                .httpBasic(basic -> basic.disable())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers(new AntPathRequestMatcher("/users", "POST")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/auth", "POST")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/docs/index.html", "GET")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/docs-park.html", "GET")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/docs-park/**", "GET")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/api-docs/**", "GET")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui-custom.html", "GET")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui.html", "GET")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/swagger-ui/**", "GET")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/**.html", "GET")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher("/webjars/**", "GET")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher( "/configurations/**", "GET")).permitAll()
                        .requestMatchers(new AntPathRequestMatcher( "/swagger-resources/**", "GET")).permitAll()
//                        .requestMatchers(HttpMethod.POST, "/users").permitAll()
//                        .requestMatchers(HttpMethod.POST, "/auth").permitAll()
                        .anyRequest().authenticated()
                ).sessionManagement(
                        session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                ).addFilterBefore(
                        jwtAuthorizationFilter(), UsernamePasswordAuthenticationFilter.class
                ).exceptionHandling(ex -> ex
                        .authenticationEntryPoint(new JwtAuthenticationEntryPoint()))
                .build();
    }

    @Bean
    public JwtAuthorizationFilter jwtAuthorizationFilter() {
        return new JwtAuthorizationFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }
}
