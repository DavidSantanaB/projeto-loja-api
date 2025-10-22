package com.example.projetoLoja.controller;

import com.example.projetoLoja.dto.ClienteRequestDTO;
import com.example.projetoLoja.model.Cliente;
import com.example.projetoLoja.repository.ClienteRepository;
import com.example.projetoLoja.security.AuthRequest;
import com.example.projetoLoja.security.AuthResponse;
import com.example.projetoLoja.security.JwtService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthenticationController(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder, JwtService jwtService, AuthenticationManager authenticationManager) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/registrar")
    public ResponseEntity<AuthResponse> registrar(@RequestBody ClienteRequestDTO request) {
        // (Lembre-se de adicionar 'senha' ao seu ClienteRequestDTO)
        Cliente cliente = new Cliente();
        cliente.setNome(request.getNome());
        cliente.setEmail(request.getEmail());
        // Criptografa a senha antes de salvar!
        cliente.setSenha(passwordEncoder.encode(request.getSenha())); 

        clienteRepository.save(cliente);

        String jwtToken = jwtService.generateToken(cliente);
        return ResponseEntity.ok(new AuthResponse(jwtToken));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest request) {
        // O AuthenticationManager valida o usuário e senha
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getSenha()
                )
        );
        
        // Se chegou aqui, o usuário é válido
        Cliente cliente = clienteRepository.findByEmail(request.getEmail())
                .orElseThrow(); // Já sabemos que ele existe

        String jwtToken = jwtService.generateToken(cliente);
        return ResponseEntity.ok(new AuthResponse(jwtToken));
    }
}