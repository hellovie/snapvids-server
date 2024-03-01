package io.github.hellovie.snapvids.types.file;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.exception.system.UtilException;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.types.Verifiable;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static io.github.hellovie.snapvids.common.module.file.FileExceptionType.*;

/**
 * [Domain Primitive] file identifier.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class FileIdentifier implements Verifiable {

    private static final Logger LOG = LoggerFactory.getLogger(FileIdentifier.class);

    /**
     * 文件唯一标识
     */
    private final String value;

    public FileIdentifier(String value) {
        this.value = value;
    }

    /**
     * 根据 {@link MultipartFile} 对象构造 {@link FileIdentifier} 对象。
     *
     * @param file {@link MultipartFile}
     * @return {@link FileIdentifier}
     */
    public static FileIdentifier buildByMultipartFile(MultipartFile file) {
        String md5 = calculateMD5Hex(file);
        return new FileIdentifier(md5);
    }

    /**
     * 计算文件的 MD5HEX。
     *
     * @param file 文件
     * @return 文件的 MD5HEX
     * @throws InvalidParamException 文件为 null 抛出
     * @throws UtilException         计算失败抛出
     */
    public static String calculateMD5Hex(MultipartFile file) throws InvalidParamException, UtilException {
        Validation.isNotNullOrElseThrow(file, UNABLE_TO_PARSE_NULL_FILE);

        try {
            String md5 = DigestUtils.md5Hex(file.getInputStream());
            LOG.info("[Calculation file md5Hex success]>>> filename={}, md5Hex={}", file.getOriginalFilename(), md5);
            return md5;
        } catch (IOException ex) {
            LOG.error("[Calculation file md5Hex fail]>>> filename={}, fileSize={}",
                    file.getOriginalFilename(), file.getSize());
            throw new UtilException(CALCULATE_FILE_MD5HEX_EXCEPTION, ex);
        }
    }

    /**
     * {@inheritDoc}
     *
     * @see Verifiable#verify()
     */
    @Override
    public void verify() throws InvalidParamException {
        Validation.isNotBlankOrElseThrow(value, FILE_IDENTIFIER_CANNOT_BE_EMPTY);
    }

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}