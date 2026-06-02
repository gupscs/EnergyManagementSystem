CREATE TABLE telemetry_records (
    id                  BIGSERIAL PRIMARY KEY,
    station_id          BIGINT           NOT NULL,
    current_power_kw    DOUBLE PRECISION NOT NULL,
    voltage_v           DOUBLE PRECISION NOT NULL,
    current_a           DOUBLE PRECISION NOT NULL,
    temperature_celsius DOUBLE PRECISION,
    status              VARCHAR(50)      NOT NULL,
    recorded_at         TIMESTAMP        NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_telemetry_station FOREIGN KEY (station_id) REFERENCES stations(id)
);
CREATE INDEX idx_telemetry_station_time ON telemetry_records(station_id, recorded_at DESC);
