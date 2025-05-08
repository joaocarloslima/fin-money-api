package br.com.fiap.fin_money_api.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
        return http.authorizeHttpRequests(
            auth -> auth
                //.requestMatchers("/categories/**").hasRole("ADMIN")
                .anyRequest().authenticated()
        )
        .csrf(csrf -> csrf.disable())
        .httpBasic(Customizer.withDefaults())
        .build();
    }
    
    // @Bean
    // UserDetailsService userDetailsService(){
    //     var users = List.of(
    //         User
    //             .withUsername("joao")
    //             .password("$2a$12$1DLNWZDzr4xwa.hhL1Y6Run9t8q2dc2vw54QwUh9fnxW3cy5B8z1q")
    //             .roles("ADMIN")
    //             .build(),
    //         User
    //             .withUsername("maria")
    //             .password("$2a$12$OyjhEIaHF.3/AxaNW.G.K.Zu.Pzv.B7.v9YPjXC6jm7svEhTE1Tcq")
    //             .roles("USER")
    //             .build()


    //     );
    //     return new InMemoryUserDetailsManager(users);
    // }

    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

}
