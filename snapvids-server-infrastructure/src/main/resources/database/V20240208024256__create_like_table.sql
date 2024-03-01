CREATE TABLE `like`
(
    `id`           BIGINT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'ID',
    `user_id`      BIGINT UNSIGNED                NOT NULL COMMENT '用户ID',
    `type`         TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '点赞类型(默认赞)',
    `is_like`      TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '点赞状态(1-点赞)',
    `object_type`  TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '被点赞的对象类型(默认作品)',
    `creation_id`  BIGINT UNSIGNED                NULL COMMENT '被点赞的作品ID',
    `comment_id`   BIGINT UNSIGNED                NULL COMMENT '被点赞的评论ID',
    `utc_created`  DATETIME(6)                    NOT NULL COMMENT '创建时间(UTC)',
    `utc_modified` DATETIME(6)                    NOT NULL COMMENT '更新时间(UTC)',
    `is_deleted`   TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '是否删除(1-删除)',
    PRIMARY KEY `PK_Like_On_Id` (`id`),
    CONSTRAINT `FK_Like_User_On_UserId` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FK_Like_Creation_On_CreationId` FOREIGN KEY (`creation_id`) REFERENCES `creation` (`id`),
    CONSTRAINT `FK_Like_Comment_On_CommentId` FOREIGN KEY (`comment_id`) REFERENCES `comment` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '点赞表';