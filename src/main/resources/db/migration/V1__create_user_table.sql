-- V1__create_user_table.sql

CREATE TABLE `user` (
                        `id`        BIGINT UNSIGNED NOT NULL AUTO_INCREMENT,
                        `email`     VARCHAR(191)    NOT NULL,
                        `nickname`  VARCHAR(12)     NOT NULL,
                        `password`  VARCHAR(255)    NOT NULL,
                        `created_at` DATETIME(6)    NOT NULL DEFAULT CURRENT_TIMESTAMP(6),

                        PRIMARY KEY (`id`),
                        UNIQUE KEY `uk_user_email` (`email`)
)
    ENGINE = InnoDB
DEFAULT CHARSET = utf8mb4
COLLATE = utf8mb4_0900_ai_ci;
