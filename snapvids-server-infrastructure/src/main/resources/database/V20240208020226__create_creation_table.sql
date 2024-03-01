CREATE TABLE `creation`
(
    `id`               BIGINT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'ID',
    `caption`          VARCHAR(1024)    DEFAULT ''    NOT NULL COMMENT '文案',
    `cover_id`         BIGINT UNSIGNED                NOT NULL COMMENT '作品封面图片ID',
    `volume`           TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '音量',
    `bgm_id`           BIGINT UNSIGNED                NOT NULL COMMENT '背景音乐ID',
    `bgm_volume`       TINYINT UNSIGNED DEFAULT 100   NOT NULL COMMENT 'BGM音量',
    `views`            INT UNSIGNED     DEFAULT 0     NOT NULL COMMENT '播放量',
    `type`             TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '作品类型(默认独创)',
    `visibility`       TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '作品可见范围(默认全部可见)',
    `permissions`      INT UNSIGNED     DEFAULT 0     NOT NULL COMMENT '作品权限(默认无设置任何权限)',
    `author_id`        BIGINT UNSIGNED                NOT NULL COMMENT '作者ID',
    `at_user_ids`      VARCHAR(2048)                  NOT NULL COMMENT '@的用户ID(英文逗号分割)',
    `is_audit_success` TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '审核标识(1-通过)',
    `audit_result`     VARCHAR(256)     DEFAULT ''    NOT NULL COMMENT '审核结果',
    `weight`           INT UNSIGNED     DEFAULT 1     NOT NULL COMMENT '推荐权重(0-不展示,权重越高,越容易被其他用户看见)',
    `utc_created`      DATETIME(6)                    NOT NULL COMMENT '创建时间(UTC)',
    `utc_modified`     DATETIME(6)                    NOT NULL COMMENT '更新时间(UTC)',
    `is_deleted`       TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '是否删除(1-删除)',
    PRIMARY KEY `PK_Creation_On_Id` (`id`),
    CONSTRAINT `FK_Creation_File_On_CoverId` FOREIGN KEY (`cover_id`) REFERENCES `file` (`id`),
    CONSTRAINT `FK_Creation_Bgm_On_BgmId` FOREIGN KEY (`bgm_id`) REFERENCES `bgm` (`id`),
    CONSTRAINT `FK_Creation_User_On_AuthorId` FOREIGN KEY (`author_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '作品表';