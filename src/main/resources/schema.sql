DROP TABLE if EXISTS vote CASCADE;
DROP TABLE if EXISTS vote_content;
DROP TABLE if EXISTS users CASCADE;

CREATE TABLE vote
(
    id            BIGINT NOT NULL AUTO_INCREMENT,
    title         VARCHAR(55)  DEFAULT NULL,
    detail        VARCHAR(255) DEFAULT NULL,
    age           INT          DEFAULT NULL,
    gender        VARCHAR(6)   DEFAULT NULL,
    mbti          VARCHAR(4)   DEFAULT NULL,
    created_date  TIMESTAMP    DEFAULT NULL,
    modified_date TIMESTAMP    DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE vote_content
(
    id     BIGINT NOT NULL AUTO_INCREMENT,
    imageA VARCHAR(255) DEFAULT NULL,
    imageB VARCHAR(255) DEFAULT NULL,
    titleA VARCHAR(55)  DEFAULT NULL,
    titleB VARCHAR(55)  DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE users
(
    id                 BIGINT NOT NULL AUTO_INCREMENT,
    nickname           VARCHAR(10)  DEFAULT NULL,
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
    PRIMARY KEY (id)
);

