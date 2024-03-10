package io.github.hellovie.snapvids.types.file;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.DomainPrimitive;
import io.github.hellovie.snapvids.common.types.Validation;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static io.github.hellovie.snapvids.common.module.file.FileExceptionType.FILE_SIZE_EXCEPTION;
import static io.github.hellovie.snapvids.common.module.file.FileExceptionType.UNABLE_TO_PARSE_NULL_FILE;

/**
 * [Domain Primitive] file size.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class FileSize extends DomainPrimitive {

    /**
     * 文件大小（Byte）
     */
    private final Long value;

    public FileSize(long value) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("value", value);
        verify(params);

        this.value = value;
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

    public Long getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }

    /**
     * {@inheritDoc}
     *
     * @see DomainPrimitive#verify(Map)
     */
    @Override
    protected void verify(Map<String, Object> params) throws InvalidParamException {
        // 校验文件大小
        Validation.executeWithInvalidParamException(() -> {
            long _value = (long) params.get("value");
            Validation.inLongScopeOrElseThrow(_value, 0, Long.MAX_VALUE, FILE_SIZE_EXCEPTION);
        }, FILE_SIZE_EXCEPTION);
    }
}
