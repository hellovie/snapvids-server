CREATE TABLE `follow`
(
    `id`           BIGINT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'ID',
    `following_id` BIGINT UNSIGNED                NOT NULL COMMENT '关注ID',
    `follower_id`  BIGINT UNSIGNED                NOT NULL COMMENT '粉丝ID',
    `type`         TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '关注类型(默认普通关注)',
    `remark`       VARCHAR(256)     DEFAULT ''    NOT NULL COMMENT '备注',
    `weight`       INT UNSIGNED     DEFAULT 0     NOT NULL COMMENT '查看权重(0-从未看过,权重越高,说明查看次数越频繁)',
    `utc_created`  DATETIME(6)                    NOT NULL COMMENT '创建时间(UTC)',
    `utc_modified` DATETIME(6)                    NOT NULL COMMENT '更新时间(UTC)',
    `is_deleted`   TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '是否删除(1-删除)',
    PRIMARY KEY `PK_Follow_On_Id` (`id`),
    FOREIGN KEY `FK_Follow_User_On_FollowingId` (`following_id`) REFERENCES `user` (`id`),
    FOREIGN KEY `FK_Follow_User_On_FollowerId` (`follower_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '关注表';