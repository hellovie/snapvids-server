CREATE TABLE `role`
(
    `id`           BIGINT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'ID',
    `role_key`     VARCHAR(64)                    NOT NULL COMMENT '角色标识',
    `name`         VARCHAR(64)                    NOT NULL COMMENT '角色名称',
    `utc_created`  DATETIME(6)                    NOT NULL COMMENT '创建时间(UTC)',
    `utc_modified` DATETIME(6)                    NOT NULL COMMENT '更新时间(UTC)',
    `is_deleted`   TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '是否删除(1-删除)',
    PRIMARY KEY `PK_Role_On_Id` (`id`),
    UNIQUE `UK_Role_On_RoleKey` (`role_key`(16))
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '角色表';