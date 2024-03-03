package io.github.hellovie.snapvids.interfaces.web.request;

/**
 * 完成文件上传表单。
 *
 * @author hellovie
 * @since 1.0.0
 */
public class FinishUploadRequest {

    /**
     * 文件唯一标识
     */
    private String fileKey;

    public String getFileKey() {
        return fileKey;
    }

    public void setFileKey(String fileKey) {
        this.fileKey = fileKey;
    }
}
