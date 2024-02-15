CREATE TABLE `bgm`
(
    `id`               BIGINT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'ID',
    `bgm_name`         VARCHAR(256)                   NOT NULL COMMENT 'BGM名称',
    `cover_id`         BIGINT UNSIGNED                NOT NULL COMMENT 'BGM封面图片ID',
    `music_id`         BIGINT UNSIGNED                NOT NULL COMMENT '背景音乐文件ID',
    `author_id`        BIGINT UNSIGNED                NOT NULL COMMENT '作者ID',
    `duration`         BIGINT UNSIGNED  DEFAULT 0     NOT NULL COMMENT '时长',
    `use_count`        INT UNSIGNED     DEFAULT 0     NOT NULL COMMENT '使用次数',
    `is_audit_success` TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '审核标识(1-通过)',
    `audit_result`     VARCHAR(256)     DEFAULT ''    NOT NULL COMMENT '审核结果',
    `weight`           INT UNSIGNED     DEFAULT 1     NOT NULL COMMENT '推荐权重(0-不展示,权重越高,越容易被其他用户看见)',
    `utc_created`      DATETIME(6)                    NOT NULL COMMENT '创建时间(UTC)',
    `utc_modified`     DATETIME(6)                    NOT NULL COMMENT '更新时间(UTC)',
    `is_deleted`       TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '是否删除(1-删除)',
    PRIMARY KEY `PK_Bgm_On_Id` (`id`),
    FOREIGN KEY `FK_Bgm_File_On_CoverId` (`cover_id`) REFERENCES `file` (`id`),
    FOREIGN KEY `FK_Bgm_File_On_MusicId` (`music_id`) REFERENCES `file` (`id`),
    FOREIGN KEY `FK_Bgm_User_On_AuthorId` (`author_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '背景音乐表';