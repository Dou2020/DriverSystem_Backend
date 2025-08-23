package com.DriverSystem_Back.modules.stock;

import java.io.Serializable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StockId implements Serializable {
    private Long product;
    private Long location;
}