CREATE TABLE charge_transaction (
    id                              BIGSERIAL       PRIMARY KEY,
    charge_status                   VARCHAR(30)     NOT NULL,
    charge_status_detail            TEXT,

    -- car owner snapshot
    car_owner_id                    BIGINT,
    car_owner_name                  VARCHAR(255),
    car_owner_phone                 VARCHAR(50),
    car_owner_email                 VARCHAR(255),

    -- car snapshot
    car_id                          BIGINT,
    car_unique_id                   VARCHAR(255),
    car_plate                       VARCHAR(50),
    car_model                       VARCHAR(255),
    car_plugged_at                  TIMESTAMP,
    initial_battery_level_in_percent DOUBLE PRECISION,

    -- pump snapshot
    pump_id                         BIGINT,
    pump_unique_id                  VARCHAR(255),
    pump_name                       VARCHAR(255),

    -- station snapshot
    station_id                      BIGINT,
    station_name                    VARCHAR(255),
    station_address                 VARCHAR(500),
    station_zipcode                 VARCHAR(20),
    station_longitude               NUMERIC(10, 7),
    station_latitude                NUMERIC(10, 7),

    -- charge payment
    confirm_charge_amount           NUMERIC(15, 2),
    payment_transaction_id          VARCHAR(255),
    payment_status                  VARCHAR(20),
    payment_method                  VARCHAR(100),
    payment_gateway                 VARCHAR(100),
    payment_at                      TIMESTAMP,

    -- charging session
    charging_start_at               TIMESTAMP,
    charging_end_at                 TIMESTAMP,
    charging_total_kw               NUMERIC(10, 3),

    -- session closure
    car_owner_end_confirmed_at      TIMESTAMP,
    car_unplugged_at                TIMESTAMP,

    -- idle time
    idle_time_minutes               INTEGER,
    idle_time_cost                  NUMERIC(15, 2),
    idle_payment_transaction_id     VARCHAR(255),
    idle_payment_status             VARCHAR(20),
    idle_payment_method             VARCHAR(100),
    idle_payment_gateway            VARCHAR(100),
    idle_payment_at                 TIMESTAMP,

    -- totals
    total_charge_amount             NUMERIC(15, 2),

    -- audit
    created_at                      TIMESTAMP       NOT NULL,
    created_by                      VARCHAR(255),
    updated_at                      TIMESTAMP,
    updated_by                      VARCHAR(255)
);

CREATE INDEX idx_charge_transaction_car_owner_id  ON charge_transaction (car_owner_id);
CREATE INDEX idx_charge_transaction_car_unique_id ON charge_transaction (car_unique_id);
CREATE INDEX idx_charge_transaction_pump_id       ON charge_transaction (pump_id);
CREATE INDEX idx_charge_transaction_station_id    ON charge_transaction (station_id);
CREATE INDEX idx_charge_transaction_payment_status ON charge_transaction (payment_status);