CREATE TABLE `chunk_file`
(
    `id`           BIGINT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'ID',
    `file_id`      BIGINT UNSIGNED                NOT NULL COMMENT '所属文件ID',
    `current_num`  INT UNSIGNED     DEFAULT 1     NOT NULL COMMENT '当前分片编号(从1开始)',
    `current_size` BIGINT UNSIGNED  DEFAULT 0     NOT NULL COMMENT '当前分片实际大小(Byte)',
    `chunk_size`   BIGINT UNSIGNED  DEFAULT 0     NOT NULL COMMENT '分片大小(Byte)',
    `total_size`   BIGINT UNSIGNED                NOT NULL COMMENT '文件总大小(Byte)',
    `total_chunks` INT UNSIGNED                   NOT NULL COMMENT '文件总块数',
    `utc_created`  DATETIME(6)                    NOT NULL COMMENT '创建时间(UTC)',
    `utc_modified` DATETIME(6)                    NOT NULL COMMENT '更新时间(UTC)',
    `is_deleted`   TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '是否删除(1-删除)',
    PRIMARY KEY `PK_ChunkFile_On_Id` (`id`),
    FOREIGN KEY `FK_ChunkFile_File_On_FileId` (`file_id`) REFERENCES `file` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '文件分片表';