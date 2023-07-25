DROP TABLE if EXISTS user_jpa_entity CASCADE;
DROP TABLE if EXISTS user_category;

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
    id            BIGINT NOT NULL AUTO_INCREMENT,
    imageA        VARCHAR(255) DEFAULT NULL,
    imageB        VARCHAR(255) DEFAULT NULL,
    titleA        VARCHAR(55)  DEFAULT NULL,
    titleB        VARCHAR(55)  DEFAULT NULL,
    PRIMARY KEY (id)
);

