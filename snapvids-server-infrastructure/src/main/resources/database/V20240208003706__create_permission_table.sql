CREATE TABLE `permission`
(
    `id`           BIGINT UNSIGNED AUTO_INCREMENT NOT NULL COMMENT 'ID',
    `code`         VARCHAR(64)                    NOT NULL COMMENT '权限编码',
    `pid`          BIGINT UNSIGNED                NULL COMMENT '父权限ID',
    `icon`         VARCHAR(32)      DEFAULT ''    NOT NULL COMMENT '权限图标(资源图标)',
    `name`         VARCHAR(64)                    NOT NULL COMMENT '权限名称',
    `url`          VARCHAR(256)     DEFAULT ''    NOT NULL COMMENT '权限路径(资源地址)',
    `type`         TINYINT UNSIGNED               NOT NULL COMMENT '权限类型',
    `path`         VARCHAR(3500)    DEFAULT ''    NOT NULL COMMENT '权限树路径(根节点到当前节点的父节点的路径,英文逗号分割)',
    `level`        INT UNSIGNED     DEFAULT 1     NOT NULL COMMENT '权限树层次(根节点为1)',
    `sort`         INT UNSIGNED     DEFAULT 0     NOT NULL COMMENT '权限排序(同层次节点的排序号)',
    `utc_created`  DATETIME(6)                    NOT NULL COMMENT '创建时间(UTC)',
    `utc_modified` DATETIME(6)                    NOT NULL COMMENT '更新时间(UTC)',
    `is_deleted`   TINYINT UNSIGNED DEFAULT 0     NOT NULL COMMENT '是否删除(1-删除)',
    PRIMARY KEY `PK_Permission_On_Id` (`id`),
    UNIQUE `UK_Permission_On_Code` (`code`(16)),
    FOREIGN KEY `FK_Permission_On_Pid` (`pid`) REFERENCES `permission` (`id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci COMMENT '权限表';