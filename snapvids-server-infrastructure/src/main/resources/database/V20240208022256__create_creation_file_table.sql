CREATE TABLE `creation_file`
(
    `creation_id` BIGINT UNSIGNED NOT NULL COMMENT '作品ID',
    `file_id`     BIGINT UNSIGNED NOT NULL COMMENT '文件ID',
    PRIMARY KEY `PK_CreationFile_On_CreationId_FileId` (`creation_id`, `file_id`),
    CONSTRAINT `FK_CreationFile_Creation_On_CreationId` FOREIGN KEY (`creation_id`) REFERENCES `creation` (`id`),
    CONSTRAINT `FK_CreationFile_File_On_FileId` FOREIGN KEY (`file_id`) REFERENCES `file` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '作品文件表';