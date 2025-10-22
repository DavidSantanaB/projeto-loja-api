package com.example.projetoLoja.security; 

import lombok.Data;

@Data
public class AuthRequest {
    private String email;
    private String senha;
}