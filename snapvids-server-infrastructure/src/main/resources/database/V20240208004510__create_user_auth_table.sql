CREATE TABLE `user_auth`
(
    `id`           BIGINT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'ID',
    `user_id`      BIGINT UNSIGNED                NOT NULL COMMENT '用户ID',
    `type`         TINYINT UNSIGNED               NOT NULL COMMENT '认证类型',
    `identifier`   VARCHAR(64)                    NOT NULL COMMENT '身份标识',
    `credential`   VARCHAR(256)     DEFAULT ''    NOT NULL COMMENT '密码凭证',
    `utc_created`  DATETIME(6)                    NOT NULL COMMENT '创建时间(UTC)',
    `utc_modified` DATETIME(6)                    NOT NULL COMMENT '更新时间(UTC)',
    `is_deleted`   TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '是否删除(1-删除)',
    PRIMARY KEY `PK_UserAuth_On_Id` (`id`),
    FOREIGN KEY `FK_UserAuth_User_On_UserId` (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '第三方认证表';