-- Crear tabla para tokens de reseteo de contraseña
CREATE TABLE reset_tokens (
    id BIGSERIAL PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL,
    expiry_date TIMESTAMP NOT NULL,
    used BOOLEAN NOT NULL DEFAULT FALSE
);

-- Crear índice para búsqueda rápida por token
CREATE INDEX idx_reset_tokens_token ON reset_tokens(token);

-- Crear índice para búsqueda por email y estado
CREATE INDEX idx_reset_tokens_email_used ON reset_tokens(email, used);
