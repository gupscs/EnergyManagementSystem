CREATE TABLE car_owner (
    id             BIGSERIAL PRIMARY KEY,
    name           VARCHAR(255)   NOT NULL,
    identification VARCHAR(100),
    phone          VARCHAR(50),
    email          VARCHAR(255),
    created_at     TIMESTAMP      NOT NULL,
    created_by     VARCHAR(255),
    updated_at     TIMESTAMP,
    updated_by     VARCHAR(255),
    deleted        BOOLEAN        NOT NULL DEFAULT FALSE
);

CREATE TABLE car (
    id             BIGSERIAL PRIMARY KEY,
    car_unique_id  VARCHAR(255)   NOT NULL,
    plate          VARCHAR(50),
    model          VARCHAR(255),
    car_owner_id   BIGINT         NOT NULL REFERENCES car_owner (id),
    created_at     TIMESTAMP      NOT NULL,
    created_by     VARCHAR(255),
    updated_at     TIMESTAMP,
    updated_by     VARCHAR(255),
    deleted        BOOLEAN        NOT NULL DEFAULT FALSE,
    CONSTRAINT uq_car_unique_id UNIQUE (car_unique_id)
);