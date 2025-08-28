package com.DriverSystem_Back.modules.Item.request;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "item")
public class Item {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "parent_id", nullable = false)
    private Long parentId;
    @Column(name = "product_id")
    private Long product;
    @Column(nullable = false, precision = 14, scale = 3)
    private BigDecimal quantity;
}
