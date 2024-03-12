package io.github.hellovie.snapvids.interfaces.web.controller;

import io.github.hellovie.snapvids.common.enums.FileType;
import io.github.hellovie.snapvids.common.exception.business.DataException;
import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.exception.manager.ExceptionCode;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.util.TypeConvertor;
import io.github.hellovie.snapvids.interfaces.web.handler.NonStaticResourceHttpRequestHandler;
import io.github.hellovie.snapvids.types.common.Id;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.EnumMap;

import static io.github.hellovie.snapvids.common.module.upload.UploadExceptionType.*;
import static io.github.hellovie.snapvids.interfaces.web.handler.NonStaticResourceHttpRequestHandler.*;
import static io.github.hellovie.snapvids.interfaces.web.handler.NonStaticResourceHttpRequestHandler.FileUrlType.FOREVER_URL;
import static io.github.hellovie.snapvids.interfaces.web.handler.NonStaticResourceHttpRequestHandler.FileUrlType.TEMP_URL;

/**
 * 静态资源控制器。
 * <p>Todo：处理用户请求的静态资源</p>
 *
 * @author hellovie
 * @since 1.0.0
 */
@RestController
@RequestMapping("/resources")
public class ResourceController {

    private static final Logger LOG = LoggerFactory.getLogger(ResourceController.class);

    @Resource(name = "nonStaticResourceHttpRequestHandler")
    private NonStaticResourceHttpRequestHandler nonStaticResourceHttpRequestHandler;

    private static final EnumMap<FileType, ExceptionCode> EX_MAP = new EnumMap<>(FileType.class);

    static {
        EX_MAP.put(FileType.IMAGE, IMAGE_RESOURCE_NOT_FOUND);
        EX_MAP.put(FileType.AUDIO, AUDIO_RESOURCE_NOT_FOUND);
        EX_MAP.put(FileType.VIDEO, VIDEO_RESOURCE_NOT_FOUND);
    }

    /**
     * 通过临时 URL 访问文件。
     *
     * @param id       文件 id
     * @param type     文件类型
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     */
    @GetMapping("/temp/**")
    public void accessFileByTempUrl(@RequestParam("id") String id, @RequestParam("type") String type,
                                    HttpServletRequest request, HttpServletResponse response) {

        accessFile(TEMP_URL, id, type, request, response);
    }

    /**
     * 通过 URL 访问文件。
     *
     * @param id       文件 id
     * @param type     文件类型
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     */
    @GetMapping("/**")
    public void accessFileByUrl(@RequestParam("id") String id, @RequestParam("type") String type,
                                HttpServletRequest request, HttpServletResponse response) {

        accessFile(FOREVER_URL, id, type, request, response);
    }

    /**
     * 文件访问。
     *
     * @param urlType  url 类型（临时、永久）
     * @param id       文件 id
     * @param type     文件类型
     * @param request  {@link HttpServletRequest}
     * @param response {@link HttpServletResponse}
     */
    private void accessFile(FileUrlType urlType, String id, String type,
                            HttpServletRequest request, HttpServletResponse response) {
        FileType fileType = null;
        try {
            // 参数校验
            Id fileId = new Id(Long.parseLong(id));
            Validation.isEnumNameOrElseThrow(type, FileType.class, FILE_RESOURCE_NOT_FOUND);
            fileType = (FileType) TypeConvertor.toEnum(type, FileType.class);
            request.setAttribute(FILE_URL_TYPE, urlType);
            request.setAttribute(FILE_ID, fileId);
            request.setAttribute(FILE_URL, getFullUrl(request));
            nonStaticResourceHttpRequestHandler.handleRequest(request, response);
        } catch (InvalidParamException invalidParamException) {
            throw new DataException(FILE_RESOURCE_NOT_FOUND, invalidParamException);
        } catch (DataException dataException) {
            if (EX_MAP.containsKey(fileType)) {
                throw new DataException(EX_MAP.get(fileType), dataException);
            }
            throw new DataException(FILE_RESOURCE_NOT_FOUND, dataException);
        } catch (Exception ex) {
            // java.io.IOException: 你的主机中的软件中止了一个已建立的连接。
            LOG.warn("Todo: {}", ex.getMessage());
        }
    }

    /**
     * 获取完整的请求 URL。
     *
     * @param request {@link HttpServletRequest}
     * @return 完整的请求 URL
     */
    private String getFullUrl(HttpServletRequest request) {
        return request.getRequestURL().toString() +
                (request.getQueryString() == null ? "" : "?" + request.getQueryString());
    }
}
