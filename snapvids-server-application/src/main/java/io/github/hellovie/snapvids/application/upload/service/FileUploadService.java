package io.github.hellovie.snapvids.application.upload.service;

import io.github.hellovie.snapvids.application.upload.dto.FileInfoDTO;
import io.github.hellovie.snapvids.application.upload.dto.UploadTokenDTO;
import io.github.hellovie.snapvids.application.upload.event.*;

/**
 * 文件上传服务。
 *
 * @author hellovie
 * @since 1.0.0
 */
public interface FileUploadService {

    /**
     * 初始化上传。
     *
     * @param command 初始化上传命令
     * @return 上传文件许可
     */
    UploadTokenDTO initUpload(InitUploadCommand command);

    /**
     * 完成文件上传。
     *
     * @param command 完成文件上传命令
     * @return 文件信息 DTO
     */
    FileInfoDTO finishUpload(FinishUploadCommand command);

    /**
     * 文件上传。
     *
     * @param event 文件上传事件
     */
    void upload(UploadEvent event);

    /**
     * 分片上传。
     *
     * @param event 分片上传事件
     */
    void uploadWithChunks(UploadChunksEvent event);

    /**
     * 获取分片上传进度。
     *
     * @param query 分片上传进度查询参数
     * @return 文件上传进度
     */
    UploadChunksProgressDTO getUploadProgress(UploadChunksProgressQuery query);

    /**
     * 合并分片。
     *
     * @param event 合并文件分片事件
     */
    void mergeChunks(MergeChunksEvent event);
}
