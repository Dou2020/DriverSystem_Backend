package com.DriverSystem_Back.modules.Item.response;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemResponseRepository  extends JpaRepository<ItemResponse, Long> {
    List<ItemResponse> findByQuotation(Long id);
}
