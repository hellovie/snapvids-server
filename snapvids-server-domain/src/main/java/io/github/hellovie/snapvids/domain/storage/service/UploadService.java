package io.github.hellovie.snapvids.domain.storage.service;

import io.github.hellovie.snapvids.common.exception.business.AuthException;
import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.common.exception.system.UtilException;
import io.github.hellovie.snapvids.domain.storage.event.MergePartEvent;
import io.github.hellovie.snapvids.domain.storage.event.SingleUploadEvent;
import io.github.hellovie.snapvids.domain.storage.event.UploadPartEvent;
import io.github.hellovie.snapvids.domain.storage.event.UploadProgressQuery;
import io.github.hellovie.snapvids.domain.storage.vo.UploadProgressVO;
import io.github.hellovie.snapvids.domain.storage.vo.UploadToken;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.file.FileKey;

/**
 * 上传服务。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface UploadService {

    /**
     * 令牌有效时间（单位：秒）
     */
    long TOKEN_EXPIRED_IN_SECONDS = 30 * 60;

    /**
     * 单次上传的文件最大大小（10 MB）
     */
    long SINGLE_UPLOAD_MAX_FILE_SIZE = 10 * 1024 * 1024;

    /**
     * 创建上传令牌。
     * <p>后端调用。</p>
     *
     * @param fileId   文件 id
     * @param fileHash 文件哈希
     * @return 上传令牌
     * @throws UtilException 创建失败后抛出
     */
    UploadToken createToken(Id fileId, FileKey fileHash) throws UtilException;

    /**
     * 校验上传令牌。
     * <p>前端调用，令牌只能上传指定文件。</p>
     *
     * @param token 上传令牌
     * @throws AuthException 校验失败后抛出
     */
    void checkToken(UploadToken token) throws AuthException;

    /**
     * 文件上传。
     * <p>前端调用，需要验证上传令牌。</p>
     *
     * @param event 上传文件事件
     * @throws DataException 上传失败抛出
     */
    void singleUpload(SingleUploadEvent event) throws DataException;

    /**
     * 分片上传。
     * <p>前端调用，需要验证上传令牌。</p>
     *
     * @param event 上传分片事件
     * @throws DataException 上传失败抛出
     */
    void uploadPart(UploadPartEvent event) throws DataException;

    /**
     * 查询分片上传的进度。
     * <p>前端调用，需要验证上传令牌。</p>
     *
     * @param query 查询文件上传进度的参数
     * @return 分片上传进度
     */
    UploadProgressVO getUploadProgress(UploadProgressQuery query);

    /**
     * 合并分片。
     * <p>前端调用，需要验证上传令牌。</p>
     *
     * @param event 合并分片事件
     * @throws DataException 合并失败抛出
     */
    void mergePart(MergePartEvent event) throws DataException;

    /**
     * 检查文件是否上传成功。
     * <p>后端调用，无需验证上传令牌。</p>
     *
     * @param fileHash 文件哈希
     * @return true：文件已上传
     */
    boolean checkUploaded(FileKey fileHash);
}
