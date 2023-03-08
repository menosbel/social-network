CREATE TABLE posts(
    id                  SERIAL PRIMARY KEY,
    message             VARCHAR(255) NOT NULL,
    userId              INT NOT NULL,
    createdAt           VARCHAR(300) NULL
);