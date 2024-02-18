package io.github.hellovie.snapvids.types.permission;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Verifiable;

/**
 * [Domain Primitive] permission path.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class PermissionPath implements Verifiable {

    /**
     * 值
     */
    private final String value;

    public PermissionPath(String value) {
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
        // 暂无
    }

    public String getValue() {
        return value;
    }
}
