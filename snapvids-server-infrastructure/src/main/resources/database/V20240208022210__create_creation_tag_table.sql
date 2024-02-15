CREATE TABLE `creation_tag`
(
    `creation_id` BIGINT UNSIGNED NOT NULL COMMENT '作品ID',
    `tag_id`      BIGINT UNSIGNED NOT NULL COMMENT '标签ID',
    PRIMARY KEY `PK_CreationTag_On_CreationId_TagId` (`creation_id`, `tag_id`),
    FOREIGN KEY `FK_CreationTag_Creation_On_CreationId` (`creation_id`) REFERENCES `creation` (`id`),
    FOREIGN KEY `FK_CreationTag_Tag_On_TagId` (`tag_id`) REFERENCES `tag` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '作品标签表';