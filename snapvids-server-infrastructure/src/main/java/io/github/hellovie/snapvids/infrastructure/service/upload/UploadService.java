package io.github.hellovie.snapvids.infrastructure.service.upload;

import io.github.hellovie.snapvids.common.exception.business.AuthException;
import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.common.exception.system.UtilException;
import io.github.hellovie.snapvids.infrastructure.service.upload.event.MergePartEvent;
import io.github.hellovie.snapvids.infrastructure.service.upload.event.SingleUploadEvent;
import io.github.hellovie.snapvids.infrastructure.service.upload.event.UploadPartEvent;
import io.github.hellovie.snapvids.infrastructure.service.upload.event.UploadProgressQuery;
import io.github.hellovie.snapvids.infrastructure.service.upload.vo.LocalUploadToken;
import io.github.hellovie.snapvids.infrastructure.service.upload.vo.UploadProgressVO;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.common.ValueString;
import io.github.hellovie.snapvids.types.file.FileKey;

/**
 * 上传服务。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface UploadService {

    /**
     * 临时文件访问地址的有效时间（单位：秒）
     */
    long TEMP_URL_EXPIRED_IN_SECONDS = 30 * 60;

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
     *
     * @param curUserId 当前用户 id
     * @param fileId    文件 id
     * @param fileKey   文件 key
     * @return 上传令牌
     * @throws UtilException 创建失败后抛出
     */
    LocalUploadToken createToken(Id curUserId, Id fileId, FileKey fileKey) throws UtilException;

    /**
     * 校验上传令牌。
     *
     * @param curUserId 当前用户 id
     * @param token     上传令牌
     * @throws AuthException 校验失败后抛出
     */
    void checkToken(Id curUserId, LocalUploadToken token) throws AuthException;

    /**
     * 文件上传。
     *
     * @param curUserId 当前用户 id
     * @param event     上传文件事件
     * @throws DataException 上传失败抛出
     */
    void singleUpload(Id curUserId, SingleUploadEvent event) throws DataException;

    /**
     * 分片上传。
     *
     * @param curUserId 当前用户 id
     * @param event     上传分片事件
     * @throws DataException 上传失败抛出
     */
    void uploadPart(Id curUserId, UploadPartEvent event) throws DataException;

    /**
     * 查询分片上传的进度。
     *
     * @param curUserId 当前用户 id
     * @param query     查询文件上传进度的参数
     * @return 分片上传进度
     * @throws DataException 查询失败抛出
     */
    UploadProgressVO getUploadProgress(Id curUserId, UploadProgressQuery query) throws DataException;

    /**
     * 合并分片。
     *
     * @param curUserId 当前用户 id
     * @param event     合并分片事件
     * @throws DataException 合并失败抛出
     */
    void mergePart(Id curUserId, MergePartEvent event) throws DataException;

    /**
     * 检查文件是否上传成功。
     *
     * @param curUserId 当前用户 id
     * @param fileKey   文件 key
     * @return true：文件已上传
     */
    boolean checkUploaded(Id curUserId, FileKey fileKey);

    /**
     * 获取文件访问路径。
     *
     * @param fileId 文件 id
     * @return 文件访问路径
     * @throws DataException 获取失败抛出
     */
    ValueString getUrl(Id fileId) throws DataException;

    /**
     * 获取文件临时访问路径。
     *
     * @param fileId 文件 id
     * @return 文件临时访问路径
     * @throws DataException 获取失败抛出
     */
    ValueString getTempUrl(Id fileId) throws DataException;

    /**
     * 获取文件系统路径。
     *
     * @param fileId 文件 id
     * @return 文件系统路径
     * @throws DataException 获取失败抛出
     */
    ValueString getSystemPath(Id fileId) throws DataException;
}
