CREATE TABLE ocpp_messages (
    id                    BIGSERIAL PRIMARY KEY,
    message_id            VARCHAR(255) NOT NULL,
    station_serial_number VARCHAR(255) NOT NULL,
    action                VARCHAR(100) NOT NULL,
    direction             VARCHAR(50)  NOT NULL,
    payload               TEXT         NOT NULL,
    received_at           TIMESTAMP    NOT NULL DEFAULT NOW()
);
CREATE INDEX idx_ocpp_messages_station ON ocpp_messages(station_serial_number, received_at DESC);

CREATE TABLE proxy_connections (
    id                    BIGSERIAL PRIMARY KEY,
    station_serial_number VARCHAR(255) NOT NULL UNIQUE,
    remote_address        VARCHAR(255) NOT NULL,
    status                VARCHAR(50)  NOT NULL,
    ocpp_version          VARCHAR(20)  NOT NULL,
    connected_at          TIMESTAMP,
    disconnected_at       TIMESTAMP,
    created_at            TIMESTAMP    NOT NULL DEFAULT NOW()
);
