CREATE TABLE `share`
(
    `id`               BIGINT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'ID',
    `user_id`          BIGINT UNSIGNED                NOT NULL COMMENT '用户ID',
    `from_creation_id` BIGINT UNSIGNED                NULL COMMENT '转发来源的作品ID',
    `from_comment_id`  BIGINT UNSIGNED                NULL COMMENT '转发来源的评论ID',
    `from_type`        TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '转发来源类型(默认作品)',
    `to_type`          TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '转发去处类型(默认用户)',
    `to_user_id`       BIGINT UNSIGNED                NULL COMMENT '转发去处的用户ID',
    `utc_created`      DATETIME(6)                    NOT NULL COMMENT '创建时间(UTC)',
    `utc_modified`     DATETIME(6)                    NOT NULL COMMENT '更新时间(UTC)',
    `is_deleted`       TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '是否删除(1-删除)',
    PRIMARY KEY `PK_Share_On_Id` (`id`),
    CONSTRAINT `FK_Share_User_On_UserId` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FK_Share_Creation_On_FromCreationId` FOREIGN KEY (`from_creation_id`) REFERENCES `creation` (`id`),
    CONSTRAINT `FK_Share_Creation_On_FromCommentId` FOREIGN KEY (`from_comment_id`) REFERENCES `comment` (`id`),
    CONSTRAINT `FK_Share_User_On_ToUserId` FOREIGN KEY (`to_user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '分享表';