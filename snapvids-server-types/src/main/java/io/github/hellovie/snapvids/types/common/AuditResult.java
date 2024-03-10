package io.github.hellovie.snapvids.types.common;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.DomainPrimitive;
import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * [Domain Primitive] audit result.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class AuditResult extends DomainPrimitive {

    /**
     * 是否审核成功
     */
    private final Boolean isSuccess;

    /**
     * 审核结果描述
     */
    private final String description;

    /**
     * 构造审核成功结果对象。
     *
     * @param description 审核结果描述
     * @return 审核成功结果对象
     */
    public static AuditResult success(String description) {
        if (StringUtils.isBlank(description)) {
            description = "";
        }
        return new AuditResult(Boolean.TRUE, description);
    }

    /**
     * 构造审核失败结果对象。
     *
     * @param description 审核结果描述
     * @return 审核失败结果对象
     */
    public static AuditResult fail(String description) {
        if (StringUtils.isBlank(description)) {
            description = "";
        }
        return new AuditResult(Boolean.FALSE, description);
    }

    private AuditResult(Boolean isSuccess, String description) {
        Map<String, Object> params = new HashMap<>(2);
        params.put("isSuccess", isSuccess);
        params.put("description", description);
        verify(params);

        this.isSuccess = isSuccess;
        this.description = description;
    }

    public Boolean getSuccess() {
        return isSuccess;
    }

    public String getDescription() {
        return description;
    }

    @Override
    public String toString() {
        return isSuccess + "#" + description;
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
