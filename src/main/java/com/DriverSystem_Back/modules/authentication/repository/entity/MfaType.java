package com.DriverSystem_Back.modules.authentication.repository.entity;

public enum MfaType {
    TOTP,  // Time-based One-Time Password (ej. Google Authenticator)
    EMAIL, // Código enviado por email
    SMS    // Código enviado por SMS
}
