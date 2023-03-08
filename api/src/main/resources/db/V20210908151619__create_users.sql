CREATE TABLE users(
    id                  SERIAL PRIMARY KEY,
    username            VARCHAR(20) NOT NULL,
    password            VARCHAR(20) NOT NULL,
    session_token       VARCHAR(300) NULL
);

CREATE UNIQUE INDEX users_username_uq ON users(LOWER(username));
