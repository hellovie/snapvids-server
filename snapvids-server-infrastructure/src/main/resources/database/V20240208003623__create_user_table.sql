CREATE TABLE `user`
(
    `id`              BIGINT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'ID',
    `username`        VARCHAR(32)                    NOT NULL COMMENT '用户名',
    `password`        VARCHAR(256)                   NOT NULL COMMENT '密码(加密)',
    `phone_number`    VARCHAR(64)                    NOT NULL COMMENT '手机号码',
    `last_login_ip`   INT UNSIGNED                   NOT NULL COMMENT '最后登录IP',
    `last_login_time` DATETIME(6)                    NOT NULL COMMENT '最后登录时间',
    `register_ip`     INT UNSIGNED                   NOT NULL COMMENT '注册IP',
    `register_time`   DATETIME(6)                    NOT NULL COMMENT '注册时间',
    `state`           TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '用户状态(0-启用)',
    `utc_created`     DATETIME(6)                    NOT NULL COMMENT '创建时间(UTC)',
    `utc_modified`    DATETIME(6)                    NOT NULL COMMENT '更新时间(UTC)',
    `is_deleted`      TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '是否删除(1-删除)',
    PRIMARY KEY `PK_User_On_Id` (`id`),
    UNIQUE `UK_User_On_Username` (`username`(16)),
    INDEX `IDX_User_On_PhoneNumber` (`phone_number`(11))
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '用户表';