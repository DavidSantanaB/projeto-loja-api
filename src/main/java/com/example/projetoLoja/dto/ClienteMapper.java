package com.example.projetoLoja.dto;

import com.example.projetoLoja.model.Cliente;

public class ClienteMapper {

    public static ClienteDTO toDTO(Cliente cliente) {
        if (cliente == null) {
            return null;
        }
        ClienteDTO dto = new ClienteDTO();
        dto.setId(cliente.getId());
        dto.setNome(cliente.getNome());
        dto.setEmail(cliente.getEmail());
        return dto;
    }

    public static Cliente toEntity(ClienteRequestDTO dto) {
        if (dto == null) {
            return null;
        }
        Cliente cliente = new Cliente();
        cliente.setNome(dto.getNome());
        cliente.setEmail(dto.getEmail());
        return cliente;
    }
}