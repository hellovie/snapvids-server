package io.github.hellovie.snapvids.types.permission;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.types.Verifiable;

import static io.github.hellovie.snapvids.common.module.user.UserExceptionType.WRONG_PERMISSION_SORT_NUMBER;

/**
 * [Domain Primitive] permission sort.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class PermissionSort implements Verifiable {

    /**
     * 值
     */
    private final Integer value;

    public PermissionSort(Integer value) {
        this.value = value;
        verify();
    }

    /**
     * {@inheritDoc}
     *
     * @see Verifiable#verify()
     */
    @Override
    public void verify() throws InvalidParamException {
        // 权限排序编号不超过 100
        Validation.inIntScopeOrElseThrow(value, 0, 100, WRONG_PERMISSION_SORT_NUMBER);
    }

    public Integer getValue() {
        return value;
    }
}
