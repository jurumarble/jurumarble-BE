DROP TABLE if EXISTS vote CASCADE;
DROP TABLE if EXISTS vote_content;
DROP TABLE if EXISTS users CASCADE;
DROP TABLE if EXISTS vote_result;
DROP TABLE if EXISTS drink;
DROP TABLE if EXISTS vote_drink_content;
DROP TABLE if EXISTS enjoy_drink;

CREATE TABLE vote
(
    id              BIGINT NOT NULL AUTO_INCREMENT,
    posted_user_id  BIGINT NOT NULL,
    title           VARCHAR(55)  DEFAULT NULL,
    detail          VARCHAR(255) DEFAULT NULL,
    filtered_age    VARCHAR(10)  DEFAULT NULL,
    filtered_gender VARCHAR(6)   DEFAULT NULL,
    filtered_mbti   VARCHAR(4)   DEFAULT NULL,
    vote_type       VARCHAR(6)   DEFAULT NULL,
    created_date    TIMESTAMP    DEFAULT NULL,
    modified_date   TIMESTAMP    DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE vote_content
(
    id      BIGINT NOT NULL AUTO_INCREMENT,
    vote_id BIGINT NOT NULL,
    imageA  VARCHAR(255) DEFAULT NULL,
    imageB  VARCHAR(255) DEFAULT NULL,
    titleA  VARCHAR(55)  DEFAULT NULL,
    titleB  VARCHAR(55)  DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users
(
    id                 BIGINT NOT NULL AUTO_INCREMENT,
    nickname           VARCHAR(20)  DEFAULT NULL,
    email              VARCHAR(55)  DEFAULT NULL,
    password           VARCHAR(15)  DEFAULT NULL,
    image_url          VARCHAR(255) DEFAULT NULL,
    age                INTEGER      DEFAULT NULL,
    gender             VARCHAR(6)   DEFAULT NULL,
    mbti               VARCHAR(4)   DEFAULT NULL,
    provider_type      VARCHAR(10)  DEFAULT NULL,
    provider_id        VARCHAR(255) DEFAULT NULL,
    created_date       TIMESTAMP    DEFAULT NULL,
    modified_date      TIMESTAMP    DEFAULT NULL,
    modified_mbti_date TIMESTAMP    DEFAULT NULL,
    deleted_date       TIMESTAMP    DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE vote_result
(
    id            BIGINT NOT NULL AUTO_INCREMENT,
    vote_id       BIGINT NOT NULL,
    voted_user_id BIGINT NOT NULL,
    choice        VARCHAR(2) DEFAULT NULL,
    created_date  TIMESTAMP  DEFAULT NULL,
    modified_date TIMESTAMP  DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE vote_drink_content
(
    id           BIGINT NOT NULL AUTO_INCREMENT,
    vote_id      BIGINT NOT NULL,
    drink_a_id   BIGINT NOT NULL,
    drink_b_id   BIGINT NOT NULL,
    drink_a_name VARCHAR(30) DEFAULT NULL,
    drink_a_type VARCHAR(10) DEFAULT NULL,
    drink_b_name VARCHAR(30) DEFAULT NULL,
    drink_b_type VARCHAR(10) DEFAULT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE drink
(
    id                  BIGINT NOT NULL AUTO_INCREMENT,
    name                VARCHAR(30)  DEFAULT NULL,
    type                VARCHAR(10)  DEFAULT NULL,
    product_name        VARCHAR(50)  DEFAULT NULL,
    alcoholic_beverage  VARCHAR(100) DEFAULT NULL,
    raw_material        VARCHAR(255) DEFAULT NULL,
    capacity            INTEGER      DEFAULT NULL,
    manufacture_address VARCHAR(255) DEFAULT NULL,
    price               INTEGER      DEFAULT NULL,
    image               VARCHAR(255) DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE enjoy_drink
(
    id      BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    vote_id BIGINT NOT NULL,
    created_date    TIMESTAMP    DEFAULT NULL,
    modified_date   TIMESTAMP    DEFAULT NULL,
    PRIMARY KEY (id)
);

