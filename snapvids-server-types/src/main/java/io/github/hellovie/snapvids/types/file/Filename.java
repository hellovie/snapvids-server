package io.github.hellovie.snapvids.types.file;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.DomainPrimitive;
import io.github.hellovie.snapvids.common.types.Validation;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

import static io.github.hellovie.snapvids.common.module.file.FileExceptionType.FILENAME_CANNOT_BE_EMPTY;
import static io.github.hellovie.snapvids.common.module.file.FileExceptionType.UNABLE_TO_PARSE_NULL_FILE;

/**
 * [Domain Primitive] file name.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class Filename extends DomainPrimitive {

    /**
     * 文件名
     */
    private final String value;

    public Filename(String value) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("value", value);
        verify(params);

        this.value = value;
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

    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }

    /**
     * {@inheritDoc}
     *
     * @see DomainPrimitive#verify(Map)
     */
    @Override
    protected void verify(Map<String, Object> params) throws InvalidParamException {
        // 校验文件名
        Validation.executeWithInvalidParamException(() -> {
            String _value = (String) params.get("value");
            Validation.isNotBlankOrElseThrow(_value, FILENAME_CANNOT_BE_EMPTY);
        }, FILENAME_CANNOT_BE_EMPTY);
    }
}
