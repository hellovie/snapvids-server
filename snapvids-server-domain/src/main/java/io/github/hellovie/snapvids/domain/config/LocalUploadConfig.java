package io.github.hellovie.snapvids.domain.config;

/**
 * Local Upload Configuration.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class LocalUploadConfig {

    private LocalUploadConfig() {}

    /**
     * 上传的文件 id
     */
    public static final String TOKEN_KEY_FILE_ID = "file_id";

    /**
     * 文件哈希值
     */
    public static final String TOKEN_KEY_FILE_HASH = "file_hash";

    /**
     * 上传的用户 id
     */
    public static final String TOKEN_KEY_USER_ID = "user_id";

    /**
     * 本地上传文件的根路径
     */
    public static final String FILE_ROOT_PATH = "H:/snapvids-file-server";
}
