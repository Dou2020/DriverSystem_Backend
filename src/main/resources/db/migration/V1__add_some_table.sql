-- =========================================================
-- Taller mecánico - Esquema base de datos
-- =========================================================


-- ============================
-- Seguridad / Usuarios
-- ============================
CREATE TABLE app_role (
                          id BIGSERIAL PRIMARY KEY,
                          code text UNIQUE NOT NULL,   -- ADMIN, EMPLOYEE, SPECIALIST, CUSTOMER, SUPPLIER
                          name text NOT NULL
);

CREATE TABLE app_user (
                          id BIGSERIAL PRIMARY KEY,

    -- credenciales / acceso
                          username text NOT NULL,
                          email    text NOT NULL,
                          phone    text NOT NULL  UNIQUE,
                          password_hash text NOT NULL,
                          is_active boolean NOT NULL DEFAULT true,
                          created_at timestamptz NOT NULL DEFAULT now(),

    -- datos de negocio
                          user_type text NOT NULL CHECK (user_type IN ('PERSON','ORGANIZATION')),
                          doc_type  text,
                          doc_number text,
                          name text NOT NULL,
                          usa_mfa boolean NOT NULL DEFAULT false
);
CREATE UNIQUE INDEX uq_app_user_username_ci ON app_user (lower(username));
CREATE UNIQUE INDEX uq_app_user_email_ci    ON app_user (lower(email));
CREATE UNIQUE INDEX uq_app_user_doc ON app_user(doc_type, doc_number)
    WHERE doc_type IS NOT NULL AND doc_number IS NOT NULL;

CREATE TABLE user_role (
                           user_id BIGINT NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
                           role_id BIGINT NOT NULL REFERENCES app_role(id) ON DELETE RESTRICT,
                           PRIMARY KEY (user_id, role_id)
);

-- MFA y recuperación de contraseña
CREATE TABLE user_mfa (
                          code text PRIMARY KEY,
                          user_id BIGINT NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
                          mfa_type text NOT NULL CHECK (mfa_type IN ('TOTP','EMAIL','SMS')),
                          enabled boolean NOT NULL DEFAULT true,
                          ts_expired TIMESTAMPTZ NOT NULL
);

CREATE TABLE password_reset (
                                id BIGSERIAL PRIMARY KEY,
                                user_id BIGINT NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
                                token text UNIQUE NOT NULL,      -- guarda HASH del token
                                expires_at timestamptz NOT NULL,
                                used_at timestamptz
);

-- ============================
-- Vehículos
-- ============================
CREATE TABLE vehicle_make (
                              id BIGSERIAL PRIMARY KEY,
                              name text UNIQUE NOT NULL
);

CREATE TABLE vehicle_model (
                               id BIGSERIAL PRIMARY KEY,
                               make_id BIGINT NOT NULL REFERENCES vehicle_make(id) ON DELETE RESTRICT,
                               name text NOT NULL,
                               UNIQUE (make_id, name)
);

CREATE TABLE vehicle (
                         id BIGSERIAL PRIMARY KEY,
                     --  customer_id BIGINT NOT NULL REFERENCES app_user(id) ON DELETE RESTRICT, -- debe ser CLIENTE/CUSTOMER
                         vin   text UNIQUE,
                         plate text UNIQUE,
                         make_id  BIGINT NOT NULL REFERENCES vehicle_make(id) ON DELETE RESTRICT,
                         model_id BIGINT NOT NULL REFERENCES vehicle_model(id) ON DELETE RESTRICT,
                         model_year int CHECK (model_year BETWEEN 1950 AND extract(year from now())::int + 1),
  color text,
  created_at timestamptz NOT NULL DEFAULT now()
);
-- CREATE INDEX ix_vehicle_customer ON vehicle(customer_id);

-- Entradas / salidas del vehículo al taller
CREATE TABLE vehicle_visit (
                               id BIGSERIAL PRIMARY KEY,
                               vehicle_id  BIGINT NOT NULL REFERENCES vehicle(id) ON DELETE CASCADE,
                               customer_id BIGINT NOT NULL REFERENCES app_user(id) ON DELETE RESTRICT,
                               arrived_at  timestamptz NOT NULL DEFAULT now(),
                               departed_at timestamptz,
                               notes text
);
CREATE INDEX ix_visit_vehicle ON vehicle_visit(vehicle_id, arrived_at DESC);
