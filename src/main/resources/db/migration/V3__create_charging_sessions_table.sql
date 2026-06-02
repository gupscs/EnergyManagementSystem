CREATE TABLE charging_sessions (
    id                   BIGSERIAL PRIMARY KEY,
    user_id              BIGINT           NOT NULL,
    station_id           BIGINT           NOT NULL,
    status               VARCHAR(50)      NOT NULL,
    energy_consumed_kwh  DOUBLE PRECISION,
    started_at           TIMESTAMP,
    ended_at             TIMESTAMP,
    created_at           TIMESTAMP        NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_session_user    FOREIGN KEY (user_id)    REFERENCES users(id),
    CONSTRAINT fk_session_station FOREIGN KEY (station_id) REFERENCES stations(id)
);
