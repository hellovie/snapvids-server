package io.github.hellovie.snapvids.types.permission;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.DomainPrimitive;

import java.util.HashMap;
import java.util.Map;

/**
 * [Domain Primitive] permission url.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class PermissionUrl extends DomainPrimitive {

    /**
     * 值
     */
    private final String value;

    public PermissionUrl(String value) {
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
        // 暂无
    }
}
