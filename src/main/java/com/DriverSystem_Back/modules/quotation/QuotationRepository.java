package com.DriverSystem_Back.modules.quotation;
import com.DriverSystem_Back.modules.Item.response.ItemResponse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuotationRepository extends JpaRepository<Quotation, Long> {
}
