CREATE TABLE notifications (
    id          BIGSERIAL PRIMARY KEY,
    user_id     BIGINT        NOT NULL,
    type        VARCHAR(100)  NOT NULL,
    title       VARCHAR(255)  NOT NULL,
    message     VARCHAR(1000) NOT NULL,
    read        BOOLEAN       NOT NULL DEFAULT FALSE,
    created_at  TIMESTAMP     NOT NULL DEFAULT NOW(),
    CONSTRAINT fk_notification_user FOREIGN KEY (user_id) REFERENCES users(id)
);
