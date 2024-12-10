package it.ticket.platform.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfiguration {

    @Bean
    SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests()

            .requestMatchers("/api/admin/**").hasAuthority("ADMIN")
            .requestMatchers("/api/tickets/**").hasAuthority("ADMIN")

 
            .requestMatchers("/admin/**").hasAuthority("ADMIN")
            .requestMatchers("/tickets/create", "/tickets/edit/**").hasAuthority("ADMIN")
            .requestMatchers("/tickets", "/tickets/**").authenticated() 
            .requestMatchers("/**").permitAll() 


            .and().formLogin()
                .defaultSuccessUrl("/tickets", true)
            .and().logout()
                .logoutSuccessUrl("/") 
            .and().csrf().disable(); 

        return http.build();
    }

    @Bean
    DatabaseUserDetailsService userDetailService() {
        return new DatabaseUserDetailsService();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    @Bean
    DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }
}

