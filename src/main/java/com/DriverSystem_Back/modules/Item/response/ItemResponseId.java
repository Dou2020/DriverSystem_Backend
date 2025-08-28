package com.DriverSystem_Back.modules.Item.response;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;
import java.util.Objects;

@Embeddable
public class ItemResponseId implements Serializable {

    @Column(name = "quotation")
    private Long quotationId;

    @Column(name = "product_id")
    private Long productId;

    public ItemResponseId() {}

    public ItemResponseId(Long quotationId, Long productId) {
        this.quotationId = quotationId;
        this.productId = productId;
    }

    // Getters y setters
    public Long getQuotationId() { return quotationId; }
    public void setQuotationId(Long quotationId) { this.quotationId = quotationId; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    // equals y hashCode
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ItemResponseId)) return false;
        ItemResponseId that = (ItemResponseId) o;
        return Objects.equals(quotationId, that.quotationId) &&
                Objects.equals(productId, that.productId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(quotationId, productId);
    }
}
