package com.DriverSystem_Back.modules.Item.request;

import java.math.BigDecimal;

public record ItemRequest (
        Long productId,
        BigDecimal quantity
) {

}
