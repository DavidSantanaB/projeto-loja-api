package com.example.projetoLoja.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.projetoLoja.model.Pedido;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, Long> {
}