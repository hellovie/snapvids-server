package io.github.hellovie.snapvids.domain.storage.service;

import io.github.hellovie.snapvids.common.exception.system.UtilException;
import io.github.hellovie.snapvids.domain.storage.event.CheckUploadedCommand;
import io.github.hellovie.snapvids.domain.storage.event.GenUploadTokenCommand;
import io.github.hellovie.snapvids.domain.storage.event.GetTempUrlQuery;
import io.github.hellovie.snapvids.domain.storage.event.GetUrlQuery;
import io.github.hellovie.snapvids.domain.storage.vo.UploadToken;
import io.github.hellovie.snapvids.types.common.ValueString;

/**
 * 文件存储服务。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface StorageService {

    /**
     * 生成上传凭证。
     *
     * @param command 生成上传令牌命令
     * @return 上传凭证
     * @throws UtilException 生成失败抛出
     */
    UploadToken generateUploadToken(GenUploadTokenCommand command) throws UtilException;

    /**
     * 获取文件访问路径。
     *
     * @param query 获取 URL 的查询参数
     * @return 文件访问路径
     * @throws UtilException 获取失败抛出
     */
    ValueString getUrl(GetUrlQuery query) throws UtilException;

    /**
     * 获取文件临时访问路径。
     *
     * @param query 获取临时 URL 的查询参数
     * @return 文件临时访问路径
     * @throws UtilException 获取失败抛出
     */
    ValueString getTempUrl(GetTempUrlQuery query) throws UtilException;

    /**
     * 检查文件是否上传成功。
     *
     * @param command 检查文件是否已上传命令
     * @return 成功上传返回 true
     */
    boolean checkUploaded(CheckUploadedCommand command);
}
