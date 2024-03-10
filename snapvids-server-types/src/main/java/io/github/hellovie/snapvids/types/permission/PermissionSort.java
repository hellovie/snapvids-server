package io.github.hellovie.snapvids.types.permission;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.DomainPrimitive;
import io.github.hellovie.snapvids.common.types.Validation;

import java.util.HashMap;
import java.util.Map;

import static io.github.hellovie.snapvids.common.module.user.UserExceptionType.WRONG_PERMISSION_SORT_NUMBER;

/**
 * [Domain Primitive] permission sort.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class PermissionSort extends DomainPrimitive {

    /**
     * 值
     */
    private final Integer value;

    public PermissionSort(int value) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("value", value);
        verify(params);

        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    /**
     * {@inheritDoc}
     *
     * @see DomainPrimitive#verify(Map)
     */
    @Override
    protected void verify(Map<String, Object> params) throws InvalidParamException {
        // 校验文件排序号
        Validation.executeWithInvalidParamException(() -> {
            int _value = (int) params.get("value");
            // 权限排序编号不超过 100
            Validation.inIntScopeOrElseThrow(_value, 0, 100, WRONG_PERMISSION_SORT_NUMBER);
        }, WRONG_PERMISSION_SORT_NUMBER);
    }
}
