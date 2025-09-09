package com.example.projetoLoja.dto;

import com.example.projetoLoja.model.Pedido;

public class PedidoMapper {

    public static PedidoResponseDTO toResponseDTO(Pedido pedido) {
        if (pedido == null) {
            return null;
        }
        PedidoResponseDTO dto = new PedidoResponseDTO();
        dto.setId(pedido.getId());
        dto.setValorTotal(pedido.getValorTotal());
        dto.setDataPedido(pedido.getDataPedido());
        dto.setStatus(pedido.getStatus());
        if (pedido.getCliente() != null) {
            dto.setClienteId(pedido.getCliente().getId());
        }
        return dto;
    }
}