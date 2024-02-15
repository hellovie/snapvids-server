CREATE TABLE `user_role`
(
    `user_id` BIGINT UNSIGNED NOT NULL COMMENT '用户ID',
    `role_id` BIGINT UNSIGNED NOT NULL COMMENT '角色ID',
    PRIMARY KEY `PK_UserRole_On_UserId_RoleId` (`user_id`, `role_id`),
    FOREIGN KEY `FK_UserRole_User_On_UserId` (`user_id`) REFERENCES `user` (`id`),
    FOREIGN KEY `FK_UserRole_Role_On_RoleId` (`role_id`) REFERENCES `role` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '用户角色表';