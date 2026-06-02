CREATE TABLE payments (
    id              BIGSERIAL PRIMARY KEY,
    user_id         BIGINT          NOT NULL,
    session_id      BIGINT          NOT NULL,
    amount          NUMERIC(10,2)   NOT NULL,
    currency        VARCHAR(10)     NOT NULL DEFAULT 'BRL',
    status          VARCHAR(50)     NOT NULL,
    transaction_id  VARCHAR(255),
    created_at      TIMESTAMP       NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_payment_user    FOREIGN KEY (user_id)    REFERENCES users(id),
    CONSTRAINT fk_payment_session FOREIGN KEY (session_id) REFERENCES charging_sessions(id)
);
