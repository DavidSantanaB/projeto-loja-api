package com.example.projetoLoja.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.projetoLoja.repository.ClienteRepository;


@Configuration
public class ApplicationConfig {

    private final ClienteRepository clienteRepository;

    public ApplicationConfig(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> clienteRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado"));
    }

@Bean
public AuthenticationProvider authenticationProvider(
        PasswordEncoder passwordEncoder, 
        UserDetailsService userDetailsService) {
    
    // 1. Use o construtor vazio (padrão)
    DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    
    // 2. Configure o UserDetailsService
    authProvider.setUserDetailsService(userDetailsService);
    
    // 3. Configure o PasswordEncoder
    authProvider.setPasswordEncoder(passwordEncoder);
    
    return authProvider;
}

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}