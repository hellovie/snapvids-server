package io.github.hellovie.snapvids.types.permission;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Verifiable;

/**
 * [Domain Primitive] permission url.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class PermissionUrl implements Verifiable {

    /**
     * 值
     */
    private final String value;

    public PermissionUrl(String value) {
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

    @Override
    public String toString() {
        return value;
    }
}
