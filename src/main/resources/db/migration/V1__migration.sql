CREATE TABLE user_entity (
    id         SERIAL PRIMARY KEY,
    username   VARCHAR(64)   NOT NULL UNIQUE,
    password   VARCHAR(2048) NOT NULL,
    role       VARCHAR(32)   NOT NULL,
    first_name VARCHAR(64)   NOT NULL,
    last_name  VARCHAR(64)   NOT NULL,
    enabled    BOOLEAN       NOT NULL DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE post_entity (
    id         SERIAL PRIMARY KEY,
    title      VARCHAR(64)   NOT NULL,
    content    VARCHAR(64)   NOT NULL
);
