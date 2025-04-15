-- 1. Создаём базу данных (выполняется отдельно!)
-- CREATE DATABASE IF NOT EXISTS distcomp;

-- 2. Подключаемся к базе (если в psql)
-- \c distcomp

-- 3. Создаём схему (если ещё не создана)
CREATE SCHEMA IF NOT EXISTS public;

-- 4. Устанавливаем схему по умолчанию
SET search_path TO public;

-- 5. Создаём таблицу пользователей (создателей)
CREATE TABLE IF NOT EXISTS tbl_creator (
                                           id BIGSERIAL PRIMARY KEY,
                                           login VARCHAR(255),
                                           password VARCHAR(255),
                                           firstname VARCHAR(255),
                                           lastname VARCHAR(255)
);

-- 6. Таблица меток
CREATE TABLE IF NOT EXISTS tbl_mark (
                                        id BIGSERIAL PRIMARY KEY,
                                        name VARCHAR(255) UNIQUE
);

-- 7. Таблица историй (без связей)
CREATE TABLE IF NOT EXISTS tbl_story (
                                         id BIGSERIAL PRIMARY KEY,
                                         creator_id BIGINT,
                                         title VARCHAR(64),
                                         content VARCHAR(2048),
                                         created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                                         modified TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- 8. Таблица уведомлений
CREATE TABLE IF NOT EXISTS tbl_notice (
                                      id BIGSERIAL PRIMARY KEY,
                                      story_id BIGINT,
                                      content TEXT,
                                      created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE story_mark (
                            story_id BIGINT NOT NULL,
                            mark_id BIGINT NOT NULL,
                            PRIMARY KEY (story_id, mark_id),
                            FOREIGN KEY (story_id) REFERENCES tbl_story(id) ON UPDATE CASCADE,
                            FOREIGN KEY (mark_id) REFERENCES tbl_mark(id) ON UPDATE CASCADE
);

-- 9. Таблица связей между историями и метками
