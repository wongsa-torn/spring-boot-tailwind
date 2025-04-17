package dev.wongweb.tailwind.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
// import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
// import org.springframework.security.core.userdetails.User;
// import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
// import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
// import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import dev.wongweb.tailwind.service.CustomUserDetailsService;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;

    public SecurityConfig(CustomUserDetailsService customUserDetailsService) {
        this.customUserDetailsService = customUserDetailsService;
    }

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/main.css").permitAll()
                        // .requestMatchers(HttpMethod.POST, "/management/add").permitAll() // ✅ อนุญาต
                        // POST
                        // // /management/add
                        // .requestMatchers("/management/**").authenticated() // ✅ ปล่อยให้เข้า
                        // /management ได้หลัง login
                        // .requestMatchers("/management/**").permitAll()
                        // .requestMatchers(HttpMethod.POST, "/management/save").permitAll()
                        .requestMatchers(HttpMethod.POST, "/management/save").permitAll()
                        .anyRequest().authenticated())
                .formLogin(form -> form
                        .loginPage("/login")
                        .permitAll())
                .logout((logout) -> logout.permitAll())
                .build();
    }

    // @Bean
    // UserDetailsService userDetailsService() {
    // UserDetails user = User
    // .withUsername("dan")
    // .password("{noop}password")
    // .build();
    // return new InMemoryUserDetailsManager(user);
    // }

    // @Bean
    // public AuthenticationManager authManager(HttpSecurity http) throws Exception
    // {
    // return http.getSharedObject(AuthenticationManagerBuilder.class)
    // .userDetailsService(customUserDetailsService)
    // .passwordEncoder(passwordEncoder())
    // .and()
    // .build();
    // }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(customUserDetailsService);
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    // แบบ เข้ารหัส BCrypt use to Encoder
    // @Bean
    // public PasswordEncoder passwordEncoder() {
    // return new BCryptPasswordEncoder();
    // }

    // แบบ plain text ไม่เข้ารหัส
    @SuppressWarnings("deprecation")
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    // หากยังไม่มี CustomUserDetailsService ใน bean สามารถสร้างแบบ @Bean ก็ได้
    @Bean
    public UserDetailsService userDetailsService() {
        return customUserDetailsService;
    }

}