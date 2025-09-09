package com.example.projetoLoja.dto;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class PedidoDTO {

    private Long id;
    private Double valorTotal;
    private LocalDateTime dataPedido;
    private String status;
    private Long clienteId;
}