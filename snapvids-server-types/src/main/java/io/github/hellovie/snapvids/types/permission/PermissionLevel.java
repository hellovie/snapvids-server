package io.github.hellovie.snapvids.types.permission;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Validation;
import io.github.hellovie.snapvids.common.types.Verifiable;

import static io.github.hellovie.snapvids.common.module.user.UserExceptionType.WRONG_PERMISSION_LEVEL;

/**
 * [Domain Primitive] permission level.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class PermissionLevel implements Verifiable {

    /**
     * 值
     */
    private final Integer value;

    public PermissionLevel(Integer value) {
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
        // 权限树层次不超过 100 层
        Validation.inIntScopeOrElseThrow(value, 1, 100, WRONG_PERMISSION_LEVEL);
    }

    public Integer getValue() {
        return value;
    }
}
