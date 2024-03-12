package io.github.hellovie.snapvids.infrastructure.service.upload.constants;

/**
 * 文件上传路径枚举。
 *
 * @author hellovie
 * @since 1.0.0
 */
public enum UploadPath {

    /**
     * 文件上传根路径
     */
    ROOT("/resources"),

    /**
     * 文件临时上传根路径
     */
    TEMP_ROOT("/resources/temp"),

    /**
     * 默认文件上传路径
     */
    DEFAULT(ROOT.getPath() + "/default");


    /**
     * 上传路径
     */
    private final String path;

    UploadPath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
