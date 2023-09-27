DROP TABLE if EXISTS vote CASCADE;
DROP TABLE if EXISTS vote_content;
DROP TABLE if EXISTS users CASCADE;
DROP TABLE if EXISTS vote_result;
DROP TABLE if EXISTS drink;
DROP TABLE if EXISTS vote_drink_content;
DROP TABLE if EXISTS bookmark;
DROP TABLE if EXISTS enjoy_drink;
DROP TABLE if EXISTS comment_emotion;
DROP TABLE if EXISTS comment;
DROP TABLE if EXISTS notification;

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
    imageA  VARCHAR(500) DEFAULT NULL,
    imageB  VARCHAR(500) DEFAULT NULL,
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
    image_url          VARCHAR(500) DEFAULT NULL,
    age                INTEGER      DEFAULT NULL,
    gender             VARCHAR(6)   DEFAULT NULL,
    mbti               VARCHAR(4)   DEFAULT NULL,
    alcohol_limit      VARCHAR(6)   DEFAULT NULL,
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
    id            BIGINT NOT NULL AUTO_INCREMENT,
    vote_id       BIGINT NOT NULL,
    drink_a_id    BIGINT NOT NULL,
    drink_b_id    BIGINT NOT NULL,
    drink_a_name  VARCHAR(30)  DEFAULT NULL,
    drink_a_type  VARCHAR(10)  DEFAULT NULL,
    drink_b_name  VARCHAR(30)  DEFAULT NULL,
    drink_b_type  VARCHAR(10)  DEFAULT NULL,
    drink_a_image VARCHAR(500) DEFAULT NULL,
    drink_b_image VARCHAR(500) DEFAULT NULL,
    region        VARCHAR(10)  DEFAULT NULL,
    PRIMARY KEY (id)
);


CREATE TABLE drink
(
    id                  BIGINT NOT NULL AUTO_INCREMENT,
    name                VARCHAR(30)     DEFAULT NULL,
    type                VARCHAR(10)     DEFAULT NULL,
    product_name        VARCHAR(50)     DEFAULT NULL,
    alcoholic_beverage  VARCHAR(100)    DEFAULT NULL,
    raw_material        VARCHAR(255)    DEFAULT NULL,
    capacity            VARCHAR(50)     DEFAULT NULL,
    manufacture_address VARCHAR(255)    DEFAULT NULL,
    region              VARCHAR(10)     DEFAULT NULL,
    price               VARCHAR(50)     DEFAULT NULL,
    image               VARCHAR(500)    DEFAULT NULL,
    latitude            DECIMAL(18, 15) DEFAULT NULL,
    longitude           DECIMAL(18, 15) DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE bookmark
(
    id            BIGINT NOT NULL AUTO_INCREMENT,
    user_id       BIGINT NOT NULL,
    vote_id       BIGINT NOT NULL,
    created_date  TIMESTAMP DEFAULT NULL,
    modified_date TIMESTAMP DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE enjoy_drink
(
    id            BIGINT NOT NULL AUTO_INCREMENT,
    user_id       BIGINT NOT NULL,
    drink_id      BIGINT NOT NULL,
    created_date  TIMESTAMP DEFAULT NULL,
    modified_date TIMESTAMP DEFAULT NULL,
    PRIMARY KEY (id)
);

CREATE TABLE comment
(
    id               BIGINT       NOT NULL AUTO_INCREMENT,
    user_id          BIGINT       NOT NULL,
    vote_id          BIGINT       DEFAULT NULL,
    drink_id         BIGINT       DEFAULT NULL,
    content          VARCHAR(255) NOT NULL,
    age              VARCHAR(255) DEFAULT NULL,
    mbti             VARCHAR(4)   DEFAULT NULL,
    gender           VARCHAR(6)   DEFAULT NULL,
    like_count       INTEGER      DEFAULT NULL,
    hate_count       INTEGER      DEFAULT NULL,
    parent_id        BIGINT       DEFAULT NULL,
    restaurant_image VARCHAR(500) DEFAULT NULL,
    restaurant_name  VARCHAR(255) DEFAULT NULL,
    created_date     TIMESTAMP,
    modified_date    TIMESTAMP,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (parent_id) REFERENCES comment (id)
);

CREATE TABLE comment_emotion
(
    id         BIGINT      NOT NULL AUTO_INCREMENT,
    user_id    BIGINT      NOT NULL,
    comment_id BIGINT      NOT NULL,
    emotion    VARCHAR(20) NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES users (id),
    FOREIGN KEY (comment_id) REFERENCES comment (id)
);

CREATE TABLE notification
(
    id                BIGINT  NOT NULL AUTO_INCREMENT,
    user_id           BIGINT  NOT NULL,
    content           VARCHAR(255) DEFAULT NULL,
    url               VARCHAR(500) DEFAULT NULL,
    is_read           BOOLEAN NOT NULL,
    notification_type ENUM('VOTE', 'COMMENT', 'ADMIN_NOTIFY') NOT NULL,
    created_date      TIMESTAMP    DEFAULT NULL,
    modified_date     TIMESTAMP    DEFAULT NULL,
    FOREIGN KEY (user_id) REFERENCES users (id)
);



