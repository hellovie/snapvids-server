package io.github.hellovie.snapvids.interfaces.web.handler;

import io.github.hellovie.snapvids.common.enums.FileState;
import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.domain.fileInfo.entity.FileInfo;
import io.github.hellovie.snapvids.domain.fileInfo.service.FileInfoService;
import io.github.hellovie.snapvids.infrastructure.service.upload.UploadService;
import io.github.hellovie.snapvids.types.common.Id;
import io.github.hellovie.snapvids.types.common.ValueString;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;

import static io.github.hellovie.snapvids.common.module.upload.UploadExceptionType.FILE_RESOURCE_NOT_FOUND;

/**
 * Todo：处理用户请求的静态资源
 *
 * @author hellovie
 * @since 1.0.0
 */
@Component("nonStaticResourceHttpRequestHandler")
public class NonStaticResourceHttpRequestHandler extends ResourceHttpRequestHandler {

    private static final Logger LOG = LoggerFactory.getLogger(NonStaticResourceHttpRequestHandler.class);

    /**
     * 文件 id
     */
    public final static String FILE_ID = "file-id";

    /**
     * 文件访问 url
     */
    public final static String FILE_URL = "file-url";

    /**
     * 文件访问 url 类型
     */
    public final static String FILE_URL_TYPE = "file-url-type";

    @javax.annotation.Resource(name = "localUploadService")
    private UploadService uploadService;

    @javax.annotation.Resource(name = "fileInfoService")
    private FileInfoService fileInfoService;

    @Override
    protected Resource getResource(HttpServletRequest request) {
        try {
            Id fileId = (Id) request.getAttribute(FILE_ID);
            String fileUrl = (String) request.getAttribute(FILE_URL);
            FileUrlType fileUrlType = (FileUrlType) request.getAttribute(FILE_URL_TYPE);
            FileInfo fileInfo = fileInfoService.getValidFileInfoById(fileId);
            // 文件不合法或文件还未上传成功，无法访问。
            if (fileInfo == null || fileInfo.getState().equals(FileState.UPLOADING)) {
                throw new DataException(FILE_RESOURCE_NOT_FOUND);
            }
            // 可以在这里加权限控制，控制文件可见范围。
            // ...

            switch (fileUrlType) {
                case TEMP_URL: {
                    ValueString tempUrl = uploadService.getTempUrl(fileId);
                    if (!tempUrl.getValue().equals(fileUrl)) {
                        LOG.info("[无效的临时访问路径]>>> 文件访问路径类型={}，文件ID={}，文件访问地址={}，文件实际地址={}",
                                fileUrlType, fileId, fileUrl, tempUrl);
                        throw new DataException(FILE_RESOURCE_NOT_FOUND);
                    }
                    break;
                }
                case FOREVER_URL: {
                    ValueString url = uploadService.getUrl(fileId);
                    if (!url.getValue().equals(fileUrl)) {
                        LOG.info("[无效的永久访问路径]>>> 文件访问路径类型={}，文件ID={}，文件访问地址={}，文件实际地址={}",
                                fileUrlType, fileId, fileUrl, url);
                        throw new DataException(FILE_RESOURCE_NOT_FOUND);
                    }
                    break;
                }
                default: {
                    LOG.warn("[访问文件资源失败]>>> 文件访问路径类型={}，文件ID={}，文件访问地址={}",
                            fileUrlType, fileId, fileUrl);
                    throw new DataException(FILE_RESOURCE_NOT_FOUND);
                }
            }

            String path = uploadService.getSystemPath(fileId).getValue();
            LOG.info("[获取访问文件资源系统路径]>>> 文件系统路径={}", path);
            return new FileSystemResource(path);
        } catch (Exception ex) {
            throw new DataException(FILE_RESOURCE_NOT_FOUND, ex);
        }
    }

    /**
     * 文件访问路径类型
     */
    public enum FileUrlType {

        /**
         * 临时访问 url
         */
        TEMP_URL,

        /**
         * 永久访问 url
         */
        FOREVER_URL
    }
}
