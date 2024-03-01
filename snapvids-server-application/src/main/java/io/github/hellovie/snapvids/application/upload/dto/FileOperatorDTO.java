package io.github.hellovie.snapvids.application.upload.dto;

import io.github.hellovie.snapvids.domain.file.entity.FileOperator;

/**
 * 文件操作员 DTO。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class FileOperatorDTO {

    /**
     * id
     */
    private final Long id;

    /**
     * 用户名
     */
    private final String username;

    public FileOperatorDTO(Long id, String username) {
        this.id = id;
        this.username = username;
    }

    public Long getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    /**
     * 转换器
     */
    public static class Convertor {

        private Convertor() {}

        public static FileOperatorDTO toFileOperatorDTO(FileOperator fileOperator) {
            if (fileOperator == null) {
                return null;
            }
            long id = fileOperator.getId() != null ? fileOperator.getId().getValue() : 0;
            String username = fileOperator.getUsername() != null ? fileOperator.getUsername().getValue() : "no username";
            return new FileOperatorDTO(id, username);
        }
    }
}
