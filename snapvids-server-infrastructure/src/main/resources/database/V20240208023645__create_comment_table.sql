CREATE TABLE `comment`
(
    `id`                BIGINT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'ID',
    `creation_id`       BIGINT UNSIGNED                NOT NULL COMMENT '作品ID',
    `is_parent_comment` TINYINT UNSIGNED DEFAULT 1     NOT NULL COMMENT '是否顶层评论(1-是)',
    `pid`               BIGINT UNSIGNED                NULL COMMENT '根评论ID',
    `content`           VARCHAR(4096)    DEFAULT ''    NOT NULL COMMENT '评论内容',
    `at_user_ids`       VARCHAR(2048)                  NOT NULL COMMENT '@的用户ID(英文逗号分割)',
    `author_id`         BIGINT UNSIGNED                NOT NULL COMMENT '发布评论的用户ID',
    `reply_user_id`     BIGINT UNSIGNED                NOT NULL COMMENT '被回复的用户ID',
    `is_top`            TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '是否置顶(1-置顶)',
    `type`              TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '评论类型(默认普通评论)',
    `is_audit_success`  TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '审核标识(1-通过)',
    `audit_result`      VARCHAR(256)     DEFAULT ''    NOT NULL COMMENT '审核结果',
    `weight`            INT UNSIGNED     DEFAULT 1     NOT NULL COMMENT '推荐权重(0-不展示,权重越高,越容易被其他用户看见)',
    `utc_created`       DATETIME(6)                    NOT NULL COMMENT '创建时间(UTC)',
    `utc_modified`      DATETIME(6)                    NOT NULL COMMENT '更新时间(UTC)',
    `is_deleted`        TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '是否删除(1-删除)',
    PRIMARY KEY `PK_Comment_On_Id ` (`id`),
    FOREIGN KEY `FK_Comment_Creation_On_CreationId` (`creation_id`) REFERENCES `creation` (`id`),
    FOREIGN KEY `FK_Comment_On_Pid` (`pid`) REFERENCES `comment` (`id`),
    FOREIGN KEY `FK_Comment_User_On_AuthorId` (`author_id`) REFERENCES `user` (`id`),
    FOREIGN KEY `FK_Comment_User_On_ReplyUserId` (`reply_user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '评论表';