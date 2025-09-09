package com.example.projetoLoja.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PedidoRequestDTO {

    @NotNull(message = "O valor total é obrigatório")
    @Min(value = 0, message = "O valor total não pode ser negativo")
    private Double valorTotal;

    @NotBlank(message = "O status é obrigatório")
    private String status;

    @NotNull(message = "O ID do cliente é obrigatório")
    private Long clienteId;
}