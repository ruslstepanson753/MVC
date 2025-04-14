CREATE TABLE IF NOT EXISTS tbl_creator (
                                     id BIGSERIAL PRIMARY KEY,
                                     login VARCHAR(255),
                                     password VARCHAR(255),
                                     firstname VARCHAR(255),
                                     lastname VARCHAR(255)
);
