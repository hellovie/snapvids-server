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
        String hash = calculateHash(file);
        return new FileIdentifier(hash);
    }

    /**
     * 计算文件的哈希值。
     *
     * @param file 文件
     * @return File Hash
     * @throws InvalidParamException 文件为 null 抛出
     * @throws UtilException         计算失败抛出
     */
    public static String calculateHash(MultipartFile file) throws InvalidParamException, UtilException {
        Validation.isNotNullOrElseThrow(file, UNABLE_TO_PARSE_NULL_FILE);

        try {
            String hash = DigestUtils.sha256Hex(file.getInputStream());
            LOG.info("[计算文件哈希值成功]>>> 文件名={}，文件哈希值={}", file.getOriginalFilename(), hash);
            return hash;
        } catch (IOException ex) {
            LOG.error("[计算文件哈希值失败]>>> 文件名={}，文件哈希值={}", file.getOriginalFilename(), file.getSize());
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