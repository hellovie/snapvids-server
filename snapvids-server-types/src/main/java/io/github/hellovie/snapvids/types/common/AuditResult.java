package io.github.hellovie.snapvids.types.common;

import io.github.hellovie.snapvids.common.exception.business.InvalidParamException;
import io.github.hellovie.snapvids.common.types.Verifiable;
import org.apache.commons.lang3.StringUtils;

/**
 * [Domain Primitive] audit result.
 *
 * @author hellovie
 * @since 1.0.0
 */
public class AuditResult implements Verifiable {

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
        this.isSuccess = isSuccess;
        this.description = description;
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

    public Boolean getSuccess() {
        return isSuccess;
    }

    public String getDescription() {
        return description;
    }
}
