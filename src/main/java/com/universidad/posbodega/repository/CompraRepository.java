package com.universidad.posbodega.repository;

import com.universidad.posbodega.entity.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Long> {
}