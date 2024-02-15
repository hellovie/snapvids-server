CREATE TABLE `creation_file`
(
    `creation_id` BIGINT UNSIGNED NOT NULL COMMENT '作品ID',
    `file_id`     BIGINT UNSIGNED NOT NULL COMMENT '文件ID',
    PRIMARY KEY `PK_CreationFile_On_CreationId_FileId` (`creation_id`, `file_id`),
    FOREIGN KEY `FK_CreationFile_Creation_On_CreationId` (`creation_id`) REFERENCES `creation` (`id`),
    FOREIGN KEY `FK_CreationFile_File_On_FileId` (`file_id`) REFERENCES `file` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '作品文件表';