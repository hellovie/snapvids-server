CREATE TABLE `collect`
(
    `id`           BIGINT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'ID',
    `user_id`      BIGINT UNSIGNED                NOT NULL COMMENT '用户ID',
    `object_type`  TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '被收藏的对象类型(默认作品)',
    `creation_id`  BIGINT UNSIGNED                NULL COMMENT '被收藏的作品ID',
    `utc_created`  DATETIME(6)                    NOT NULL COMMENT '创建时间(UTC)',
    `utc_modified` DATETIME(6)                    NOT NULL COMMENT '更新时间(UTC)',
    `is_deleted`   TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '是否删除(1-删除)',
    PRIMARY KEY `PK_Collect_On_Id` (`id`),
    FOREIGN KEY `FK_Collect_User_On_UserId` (`user_id`) REFERENCES `user` (`id`),
    FOREIGN KEY `FK_Collect_Creation_On_CreationId` (`creation_id`) REFERENCES `creation` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '收藏表';