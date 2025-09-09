package com.example.projetoLoja.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.projetoLoja.dto.ClienteDTO;
import com.example.projetoLoja.dto.ClienteMapper;
import com.example.projetoLoja.dto.ClienteRequestDTO;
import com.example.projetoLoja.model.Cliente;
import com.example.projetoLoja.repository.ClienteRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/clientes")
public class ClienteController {

    @Autowired
    private ClienteRepository clienteRepository;

    // POST: Criar um novo cliente
    @PostMapping
    public ResponseEntity<ClienteDTO> criarCliente(@Valid @RequestBody ClienteRequestDTO clienteRequestDTO) {
        Cliente cliente = ClienteMapper.toEntity(clienteRequestDTO);
        Cliente novoCliente = clienteRepository.save(cliente);
        return new ResponseEntity<>(ClienteMapper.toDTO(novoCliente), HttpStatus.CREATED);
    }

    // GET: Listar todos os clientes
    @GetMapping
    public ResponseEntity<List<ClienteDTO>> listarClientes() {
        List<Cliente> clientes = clienteRepository.findAll();
        List<ClienteDTO> clientesDTO = clientes.stream()
                .map(ClienteMapper::toDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(clientesDTO);
    }

    // GET: Obter um cliente por ID
    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> obterClientePorId(@PathVariable Long id) {
        return clienteRepository.findById(id)
                .map(cliente -> ResponseEntity.ok(ClienteMapper.toDTO(cliente)))
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT: Atualizar um cliente
    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizarCliente(@PathVariable Long id, @Valid @RequestBody ClienteRequestDTO clienteAtualizadoDTO) {
        return clienteRepository.findById(id)
                .map(cliente -> {
                    cliente.setNome(clienteAtualizadoDTO.getNome());
                    cliente.setEmail(clienteAtualizadoDTO.getEmail());
                    Cliente clienteSalvo = clienteRepository.save(cliente);
                    return ResponseEntity.ok(ClienteMapper.toDTO(clienteSalvo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Deletar um cliente
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarCliente(@PathVariable Long id) {
        if (clienteRepository.existsById(id)) {
            clienteRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}