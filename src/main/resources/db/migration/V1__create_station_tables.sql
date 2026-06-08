CREATE TABLE station (
    id          BIGSERIAL PRIMARY KEY,
    name        VARCHAR(255)   NOT NULL,
    address     VARCHAR(500),
    zipcode     VARCHAR(20),
    longitude   DECIMAL(10, 7),
    latitude    DECIMAL(10, 7),
    created_at  TIMESTAMP      NOT NULL,
    created_by  VARCHAR(255),
    updated_at  TIMESTAMP,
    updated_by  VARCHAR(255),
    deleted     BOOLEAN        NOT NULL DEFAULT FALSE
);

CREATE TABLE pump (
    id                  BIGSERIAL PRIMARY KEY,
    pump_unique_id      VARCHAR(255)   NOT NULL,
    station_id          BIGINT         NOT NULL REFERENCES station (id),
    name                VARCHAR(255),
    pump_status         VARCHAR(20)    NOT NULL DEFAULT 'AVALIABLE',
    car_plugged_unique_id VARCHAR(255),
    plugged_at          TIMESTAMP,
    created_at          TIMESTAMP      NOT NULL,
    created_by          VARCHAR(255),
    updated_at          TIMESTAMP,
    updated_by          VARCHAR(255),
    deleted             BOOLEAN        NOT NULL DEFAULT FALSE,
    CONSTRAINT uq_pump_unique_id UNIQUE (pump_unique_id)
);

CREATE TABLE pump_status_history (
    id                    BIGSERIAL PRIMARY KEY,
    pump_id               BIGINT NOT NULL REFERENCES pump (id),
    car_plugged_unique_id VARCHAR(255),
    plugged_at            TIMESTAMP,
    unplugged_at          TIMESTAMP
);