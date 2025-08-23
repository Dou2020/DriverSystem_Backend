package com.DriverSystem_Back.modules.stockMovement;

import org.springframework.data.jpa.repository.JpaRepository;

public interface StockMovementRepository extends JpaRepository<StockMovement, Long> {
}