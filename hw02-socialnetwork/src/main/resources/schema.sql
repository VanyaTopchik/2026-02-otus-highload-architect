CREATE TABLE IF NOT EXISTS users
(
    id            UUID PRIMARY KEY DEFAULT gen_random_uuid(),
    first_name    VARCHAR(100) NOT NULL,
    second_name   VARCHAR(100) NOT NULL,
    birthdate     DATE         NOT NULL,
    biography     TEXT,
    gender        VARCHAR(20),
    city          VARCHAR(100),
    password_hash VARCHAR(60)  NOT NULL,
    token         UUID
);

CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE INDEX IF NOT EXISTS idx_first_name_second_name_gin ON users USING gin (first_name gin_trgm_ops, second_name gin_trgm_ops);