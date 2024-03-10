package io.github.hellovie.snapvids.types.file;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.DomainPrimitive;
import io.github.hellovie.snapvids.common.types.Validation;

import java.util.HashMap;
import java.util.Map;

import static io.github.hellovie.snapvids.common.module.file.FileExceptionType.FILE_PATH_CANNOT_BE_EMPTY;

/**
 * [Domain Primitive] file path.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class FilePath extends DomainPrimitive {

    /**
     * 文件相对路径
     */
    private final String value;

    public FilePath(String value) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("value", value);
        verify(params);

        this.value = value;
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
        // 校验文件路径
        Validation.executeWithInvalidParamException(() -> {
            String _value = (String) params.get("value");
            Validation.isNotBlankOrElseThrow(_value, FILE_PATH_CANNOT_BE_EMPTY);
        }, FILE_PATH_CANNOT_BE_EMPTY);
    }
}
