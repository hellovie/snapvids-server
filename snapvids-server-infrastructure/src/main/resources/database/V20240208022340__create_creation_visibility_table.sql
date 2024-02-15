CREATE TABLE `creation_visibility`
(
    `creation_id` BIGINT UNSIGNED NOT NULL COMMENT '作品ID',
    `user_id`     BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
    PRIMARY KEY `PK_CreationVisibility_On_CreationId_UserId` (`creation_id`, `user_id`),
    FOREIGN KEY `FK_CreationVisibility_Creation_On_CreationId` (`creation_id`) REFERENCES `creation` (`id`),
    FOREIGN KEY `FK_CreationVisibility_User_On_UserId` (`user_id`) REFERENCES `user` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '作品可见范围表';