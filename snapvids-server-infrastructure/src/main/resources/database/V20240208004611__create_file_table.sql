CREATE TABLE `file`
(
    `id`               BIGINT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'ID',
    `original_name`    VARCHAR(256)                   NOT NULL COMMENT '文件原始名',
    `storage_name`     VARCHAR(256)                   NOT NULL COMMENT '文件存储名',
    `identifier`       VARCHAR(64)                    NOT NULL COMMENT '文件唯一标识',
    `path`             VARCHAR(256)                   NOT NULL COMMENT '相对路径',
    `ext`              VARCHAR(15)                    NOT NULL COMMENT '文件后缀',
    `size`             BIGINT UNSIGNED  DEFAULT 0     NOT NULL COMMENT '文件大小(Byte)',
    `type`             TINYINT UNSIGNED               NOT NULL COMMENT '文件类型',
    `storage`          TINYINT UNSIGNED               NOT NULL COMMENT '存储方式',
    `state`            TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '文件状态(默认上传中)',
    `visibility`       TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '文件可见范围(默认全部可见)',
    `is_audit_success` TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '审核标识(1-通过)',
    `audit_result`     VARCHAR(256)     DEFAULT ''    NOT NULL COMMENT '审核结果',
    `created_by_id`    BIGINT UNSIGNED                NOT NULL COMMENT '创建者ID',
    `modified_by_id`   BIGINT UNSIGNED                NOT NULL COMMENT '更新者ID',
    `utc_created`      DATETIME(6)                    NOT NULL COMMENT '创建时间(UTC)',
    `utc_modified`     DATETIME(6)                    NOT NULL COMMENT '更新时间(UTC)',
    `is_deleted`       TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '是否删除(1-删除)',
    PRIMARY KEY `PK_File_On_Id` (`id`),
    UNIQUE `UK_File_On_StorageName` (`storage_name`(16)),
    CONSTRAINT `FK_File_User_On_CreatedById` FOREIGN KEY (`created_by_id`) REFERENCES `user` (`id`),
    CONSTRAINT `FK_File_User_On_ModifiedById` FOREIGN KEY (`modified_by_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '文件表';