CREATE TABLE `user_info`
(
    `id`            BIGINT UNSIGNED AUTO_INCREMENT                 NOT NULL COMMENT 'ID',
    `user_id`       BIGINT UNSIGNED                                NOT NULL COMMENT '用户ID',
    `cover_id`      BIGINT UNSIGNED                                NOT NULL COMMENT '用户封面ID',
    `avatar_id`     BIGINT UNSIGNED                                NOT NULL COMMENT '用户头像ID',
    `nickname`      VARCHAR(64)                                    NOT NULL COMMENT '用户昵称',
    `description`   VARCHAR(256)                                   NOT NULL COMMENT '用户自我描述',
    `first_name`    VARCHAR(64)      DEFAULT ''                    NOT NULL COMMENT '用户名字',
    `last_name`     VARCHAR(64)      DEFAULT ''                    NOT NULL COMMENT '用户姓氏',
    `gender`        TINYINT UNSIGNED DEFAULT 0                     NOT NULL COMMENT '用户性别(默认未知)',
    `birthday`      DATETIME(6)      DEFAULT '0001-01-01 00:00:00' NOT NULL COMMENT '用户生日',
    `address`       VARCHAR(256)     DEFAULT ''                    NOT NULL COMMENT '用户详细地址',
    `city`          VARCHAR(256)     DEFAULT ''                    NOT NULL COMMENT '用户所在城市',
    `state`         VARCHAR(256)     DEFAULT ''                    NOT NULL COMMENT '用户所在州/省',
    `country`       VARCHAR(256)     DEFAULT ''                    NOT NULL COMMENT '用户所在国家',
    `privacy_level` INT UNSIGNED     DEFAULT 0                     NOT NULL COMMENT '用户信息私密级别(默认全展示)',
    `utc_created`   DATETIME(6)                                    NOT NULL COMMENT '创建时间(UTC)',
    `utc_modified`  DATETIME(6)                                    NOT NULL COMMENT '更新时间(UTC)',
    `is_deleted`    TINYINT UNSIGNED DEFAULT 0                     NOT NULL COMMENT '是否删除(1-删除)',
    PRIMARY KEY `PK_UserInfo_On_Id` (`id`),
    FOREIGN KEY `FK_UserInfo_User_On_UserId` (`user_id`) REFERENCES `user` (`id`),
    FOREIGN KEY `FK_UserInfo_File_On_CoverId` (`cover_id`) REFERENCES `file` (`id`),
    FOREIGN KEY `FK_UserInfo_File_On_AvatarId` (`avatar_id`) REFERENCES `file` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '用户信息表';