package io.github.hellovie.snapvids.common.constants;

/**
 * 文件上传路径枚举。
 *
 * @author hellovie
 * @since 1.0.0
 */
public enum UploadPath {

    /**
     * 默认文件上传路径
     */
    DEFAULT("/resources/default");

    /**
     * 上传根路径
     */
    private final String root;

    UploadPath(String root) {
        this.root = root;
    }

    public String getRoot() {
        return root;
    }
}
