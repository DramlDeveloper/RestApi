CREATE TABLE IF NOT EXISTS company
(
    id      bigserial PRIMARY KEY,
    name    varchar(150) NOT NULL,
    address varchar(256)
);

CREATE TABLE IF NOT EXISTS post
(
    id    bigserial PRIMARY KEY,
    title varchar(50) NOT NULL
);

CREATE TABLE IF NOT EXISTS staff
(
    id         bigserial PRIMARY KEY,
    first_name varchar(50) NOT NULL,
    last_name  varchar(50) NOT NULL,
    company_id BIGINT REFERENCES company (id),
    post_id    BIGINT REFERENCES post (id)
);