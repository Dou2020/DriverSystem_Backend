package com.DriverSystem_Back.modules.stock;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, StockId> {
}