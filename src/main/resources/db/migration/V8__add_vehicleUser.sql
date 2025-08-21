CREATE TABLE vehicle_user (
                              id BIGSERIAL PRIMARY KEY,
                              user_id BIGINT NOT NULL REFERENCES app_user(id) ON DELETE CASCADE,
                              vehicle_id BIGINT NOT NULL REFERENCES vehicle(id) ON DELETE CASCADE,
                              CONSTRAINT uq_user_vehicle UNIQUE (user_id, vehicle_id)
);
