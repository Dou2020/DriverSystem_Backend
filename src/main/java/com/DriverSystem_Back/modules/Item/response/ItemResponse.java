package com.DriverSystem_Back.modules.Item.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "item_quotation")
public class ItemResponse {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long quotation;
    private BigDecimal quantity;
    private Long product_id;
    private String name;
    private String brand;
    private String unit;
    private BigDecimal price;
    private String categoria;


}
