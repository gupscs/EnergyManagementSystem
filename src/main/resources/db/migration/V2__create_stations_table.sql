CREATE TABLE stations (
    id              BIGSERIAL PRIMARY KEY,
    serial_number   VARCHAR(255) NOT NULL UNIQUE,
    name            VARCHAR(255) NOT NULL,
    location        VARCHAR(500) NOT NULL,
    status          VARCHAR(50)  NOT NULL,
    max_power_kw    DOUBLE PRECISION NOT NULL,
    created_at      TIMESTAMP    NOT NULL DEFAULT NOW()
);
