package io.github.hellovie.snapvids.types.file;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.types.Verifiable;
import org.springframework.web.multipart.MultipartFile;

import static io.github.hellovie.snapvids.common.module.file.FileExceptionType.FILE_SIZE_EXCEPTION;
import static io.github.hellovie.snapvids.common.module.file.FileExceptionType.UNABLE_TO_PARSE_NULL_FILE;

/**
 * [Domain Primitive] file size.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class FileSize implements Verifiable {

    /**
     * 文件大小（Byte）
     */
    private final Long value;

    public FileSize(Long value) {
        this.value = value;
        verify();
    }

    /**
     * 根据 {@link MultipartFile} 对象构造 {@link FileSize} 对象。
     *
     * @param file {@link MultipartFile}
     * @return {@link FileSize}
     */
    public static FileSize buildByMultipartFile(MultipartFile file) {
        Validation.isNotNullOrElseThrow(file, UNABLE_TO_PARSE_NULL_FILE);
        return new FileSize(file.getSize());
    }

    /**
     * {@inheritDoc}
     *
     * @see Verifiable#verify()
     */
    @Override
    public void verify() throws InvalidParamException {
        Validation.inLongScopeOrElseThrow(value, 0, Long.MAX_VALUE, FILE_SIZE_EXCEPTION);
    }

    public Long getValue() {
        return value;
    }
}
