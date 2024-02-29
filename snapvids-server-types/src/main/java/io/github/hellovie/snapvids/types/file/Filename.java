package io.github.hellovie.snapvids.types.file;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.types.Verifiable;
import org.springframework.web.multipart.MultipartFile;

import static io.github.hellovie.snapvids.common.module.file.FileExceptionType.FILENAME_CANNOT_BE_EMPTY;
import static io.github.hellovie.snapvids.common.module.file.FileExceptionType.UNABLE_TO_PARSE_NULL_FILE;

/**
 * [Domain Primitive] file name.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class Filename implements Verifiable {

    /**
     * 文件名
     */
    private final String value;

    public Filename(String value) {
        this.value = value;
        verify();
    }

    /**
     * 根据 {@link MultipartFile} 对象构造 {@link Filename} 对象。
     *
     * @param file {@link MultipartFile}
     * @return {@link Filename}
     */
    public static Filename buildByMultipartFile(MultipartFile file) {
        Validation.isNotNullOrElseThrow(file, UNABLE_TO_PARSE_NULL_FILE);
        return new Filename(file.getOriginalFilename());
    }

    /**
     * {@inheritDoc}
     *
     * @see Verifiable#verify()
     */
    @Override
    public void verify() throws InvalidParamException {
        Validation.isNotBlankOrElseThrow(value, FILENAME_CANNOT_BE_EMPTY);
    }

    public String getValue() {
        return value;
    }
}
