package io.github.hellovie.snapvids.types.permission;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.DomainPrimitive;

import java.util.HashMap;
import java.util.Map;

/**
 * [Domain Primitive] permission icon.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class PermissionIcon extends DomainPrimitive {

    /**
     * 图标名称
     */
    private final String name;

    public PermissionIcon(String name) {
        Map<String, Object> params = new HashMap<>(1);
        params.put("name", name);
        verify(params);

        this.name = name;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * {@inheritDoc}
     *
     * @see DomainPrimitive#verify(Map)
     */
    @Override
    protected void verify(Map<String, Object> params) throws InvalidParamException {
        // 暂无
    }
}
