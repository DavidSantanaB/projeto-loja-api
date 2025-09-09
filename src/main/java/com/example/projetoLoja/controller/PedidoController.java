package com.example.projetoLoja.controller;

import java.time.LocalDateTime;
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

import com.example.projetoLoja.dto.PedidoMapper;
import com.example.projetoLoja.dto.PedidoRequestDTO;
import com.example.projetoLoja.dto.PedidoResponseDTO;
import com.example.projetoLoja.model.Cliente;
import com.example.projetoLoja.model.Pedido;
import com.example.projetoLoja.repository.ClienteRepository;
import com.example.projetoLoja.repository.PedidoRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    // POST: Criar um novo pedido
    @PostMapping
    public ResponseEntity<PedidoResponseDTO> criarPedido(@Valid @RequestBody PedidoRequestDTO pedidoRequestDTO) {
        Cliente cliente = clienteRepository.findById(pedidoRequestDTO.getClienteId())
                .orElse(null);

        if (cliente == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Pedido pedido = new Pedido();
        pedido.setValorTotal(pedidoRequestDTO.getValorTotal());
        pedido.setStatus(pedidoRequestDTO.getStatus());
        pedido.setDataPedido(LocalDateTime.now());
        pedido.setCliente(cliente);

        Pedido novoPedido = pedidoRepository.save(pedido);
        return new ResponseEntity<>(PedidoMapper.toResponseDTO(novoPedido), HttpStatus.CREATED);
    }

    // GET: Listar todos os pedidos
    @GetMapping
    public ResponseEntity<List<PedidoResponseDTO>> listarPedidos() {
        List<Pedido> pedidos = pedidoRepository.findAll();
        List<PedidoResponseDTO> pedidosDTO = pedidos.stream()
                .map(PedidoMapper::toResponseDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(pedidosDTO);
    }

    // GET: Obter um pedido por ID
    @GetMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> obterPedidoPorId(@PathVariable Long id) {
        return pedidoRepository.findById(id)
                .map(pedido -> ResponseEntity.ok(PedidoMapper.toResponseDTO(pedido)))
                .orElse(ResponseEntity.notFound().build());
    }

    // PUT: Atualizar um pedido
    @PutMapping("/{id}")
    public ResponseEntity<PedidoResponseDTO> atualizarPedido(@PathVariable Long id, @Valid @RequestBody PedidoRequestDTO pedidoAtualizadoDTO) {
        return pedidoRepository.findById(id)
                .map(pedido -> {
                    pedido.setValorTotal(pedidoAtualizadoDTO.getValorTotal());
                    pedido.setStatus(pedidoAtualizadoDTO.getStatus());
                    Pedido pedidoSalvo = pedidoRepository.save(pedido);
                    return ResponseEntity.ok(PedidoMapper.toResponseDTO(pedidoSalvo));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Deletar um pedido
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletarPedido(@PathVariable Long id) {
        if (pedidoRepository.existsById(id)) {
            pedidoRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}