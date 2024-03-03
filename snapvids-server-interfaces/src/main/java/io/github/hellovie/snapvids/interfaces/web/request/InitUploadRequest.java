package io.github.hellovie.snapvids.interfaces.web.request;

/**
 * 初始化上传表单。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class InitUploadRequest {

    /**
     * 文件原始名
     */
    private String originalName;

    /**
     * 文件唯一标识
     */
    private String fileKey;

    /**
     * 文件后缀
     */
    private String ext;

    /**
     * 文件大小（Byte）
     */
    private Long size;

    public String getOriginalName() {
        return originalName;
    }

    public void setOriginalName(String originalName) {
        this.originalName = originalName;
    }

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public Long getSize() {
        return size;
    }

    public void setSize(Long size) {
        this.size = size;
    }
}
