package io.github.hellovie.snapvids.application.file.dto;

import io.github.hellovie.snapvids.common.enums.FileState;
import io.github.hellovie.snapvids.common.enums.FileType;
import io.github.hellovie.snapvids.common.enums.FileVisibility;
import io.github.hellovie.snapvids.domain.fileInfo.entity.FileInfo;
import io.github.hellovie.snapvids.domain.storage.event.GetTempUrlQuery;
import io.github.hellovie.snapvids.domain.storage.event.GetUrlQuery;
import io.github.hellovie.snapvids.domain.storage.service.StorageService;

/**
 * 文件信息 DTO。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class FileInfoDTO {

    /**
     * id
     */
    private final Long id;

    /**
     * 文件访问路径
     */
    private final String url;

    /**
     * 文件大小（Byte）
     */
    private final Long size;

    /**
     * 文件类型
     */
    private final FileType type;

    /**
     * 文件状态（默认上传中）
     */
    private final FileState state;

    /**
     * 文件可见范围（默认全部可见）
     */
    private final FileVisibility visibility;

    /**
     * 创建者
     */
    private final FileOperatorDTO createdBy;

    /**
     * 更新者
     */
    private final FileOperatorDTO modifiedBy;

    public FileInfoDTO(Long id, String url, Long size, FileType type,
                       FileState state, FileVisibility visibility, FileOperatorDTO createdBy,
                       FileOperatorDTO modifiedBy) {
        this.id = id;
        this.url = url;
        this.size = size;
        this.type = type;
        this.state = state;
        this.visibility = visibility;
        this.createdBy = createdBy;
        this.modifiedBy = modifiedBy;
    }

    public Long getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public Long getSize() {
        return size;
    }

    public FileType getType() {
        return type;
    }

    public FileState getState() {
        return state;
    }

    public FileVisibility getVisibility() {
        return visibility;
    }

    public FileOperatorDTO getCreatedBy() {
        return createdBy;
    }

    public FileOperatorDTO getModifiedBy() {
        return modifiedBy;
    }

    /**
     * 转换器
     */
    public static class Convertor {

        private Convertor() {}

        public static FileInfoDTO toFileInfoDTO(FileInfo fileInfo, StorageService storageService, boolean isTemp) {
            if (fileInfo == null) {
                return null;
            }

            long id = fileInfo.getId() != null ? fileInfo.getId().getValue() : 0L;
            long size = fileInfo.getSize() != null ? fileInfo.getSize().getValue() : 0L;
            FileOperatorDTO createdBy = FileOperatorDTO.Convertor.toFileOperatorDTO(fileInfo.getCreatedBy());
            FileOperatorDTO modifiedBy = FileOperatorDTO.Convertor.toFileOperatorDTO(fileInfo.getModifiedBy());
            String url = "no url";
            if (storageService != null && isTemp) {
                url = storageService.getTempUrl(new GetTempUrlQuery(fileInfo.getId())).getValue();
            }

            if (storageService != null && !isTemp) {
                url = storageService.getUrl(new GetUrlQuery(fileInfo.getId())).getValue();
            }
            return new FileInfoDTO(id, url, size, fileInfo.getType(),
                    fileInfo.getState(), fileInfo.getVisibility(), createdBy, modifiedBy);
        }
    }
}
