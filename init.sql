CREATE TABLE IF NOT EXISTS tbl_creator (
                                     id BIGSERIAL PRIMARY KEY,
                                     login VARCHAR(255),
                                     password VARCHAR(255),
                                     firstname VARCHAR(255),
                                     lastname VARCHAR(255)
);

CREATE TABLE tbl_story (
                       id BIGSERIAL PRIMARY KEY,
                       creator_id BIGINT,
                       title VARCHAR(64) NOT NULL,
                       content TEXT NOT NULL,
                       created TIMESTAMP WITHOUT TIME ZONE,
                       modified TIMESTAMP WITHOUT TIME ZONE
);

CREATE TABLE tbl_mark (
                          id BIGSERIAL PRIMARY KEY,
                          name VARCHAR(32) NOT NULL
);
