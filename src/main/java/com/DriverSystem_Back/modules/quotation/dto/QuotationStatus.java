package com.DriverSystem_Back.modules.quotation.dto;

public enum QuotationStatus {
    DRAFT(1),
    SENT(2),
    APPROVED(3),
    REJECTED(4),
    EXPIRED(5);
    private final int code;

    QuotationStatus(int code) {
        this.code = code;
    }

    public int getCode() {
        return code;
    }
    // Método para obtener el enum desde un código
    public static QuotationStatus fromCode(int code) {
        for (QuotationStatus status : QuotationStatus.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Código inválido: " + code);
    }
}
