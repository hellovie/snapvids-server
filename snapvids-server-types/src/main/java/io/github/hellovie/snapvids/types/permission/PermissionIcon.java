package io.github.hellovie.snapvids.types.permission;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Verifiable;

/**
 * [Domain Primitive] permission icon.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class PermissionIcon implements Verifiable {

    /**
     * 图标名称
     */
    private final String name;

    public PermissionIcon(String name) {
        this.name = name;
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

    public String getName() {
        return name;
    }
}
