CREATE TABLE `role_permission`
(
    `role_id`       BIGINT UNSIGNED NOT NULL COMMENT '角色ID',
    `permission_id` BIGINT UNSIGNED NOT NULL COMMENT '权限ID',
    PRIMARY KEY `PK_RolePermission_On_RoleId_PermissionId` (`role_id`, `permission_id`),
    FOREIGN KEY `FK_RolePermission_Role_On_RoleId` (`role_id`) REFERENCES `role` (`id`),
    FOREIGN KEY `FK_RolePermission_Permission_On_PermissionId` (`permission_id`) REFERENCES `permission` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '角色权限表';