package com.DriverSystem_Back.modules.Item.response;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Immutable;

import java.math.BigDecimal;

@Entity
@Table(name = "item_quotation")
@Immutable
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ItemResponse {

    @EmbeddedId
    private ItemResponseId id;
    private BigDecimal quantity;
    private String name;
    private String brand;
    private String unit;
    private BigDecimal price;
    private String categoria;
    private BigDecimal subtotal;
}
