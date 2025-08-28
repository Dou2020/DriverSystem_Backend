package com.DriverSystem_Back.modules.Payment.view;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PaymentViewRepository extends JpaRepository<PaymentView, Long> {
    List<PaymentView> findByUserId(Long userId);
}
