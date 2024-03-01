package io.github.hellovie.snapvids.domain.file.entity;

import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.user.Username;

/**
 * 文件操作员「创建 / 修改」。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class FileOperator {

    /**
     * id
     */
    private Id id;

    /**
     * 用户名
     */
    private Username username;

    public FileOperator(Id id) {
        this.id = id;
    }

    public FileOperator(Id id, Username username) {
        this.id = id;
        this.username = username;
    }

    public Id getId() {
        return id;
    }

    public void setId(Id id) {
        this.id = id;
    }

    public Username getUsername() {
        return username;
    }

    public void setUsername(Username username) {
        this.username = username;
    }
}
