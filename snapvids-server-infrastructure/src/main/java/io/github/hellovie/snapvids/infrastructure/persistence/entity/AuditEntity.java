package io.github.hellovie.snapvids.infrastructure.persistence.entity;

import javax.persistence.MappedSuperclass;

/**
 * 审核实体表基类。
 *
 * @author hellovie
 * @since 1.0.0
 */
@MappedSuperclass
public abstract class AuditEntity extends BaseEntity {

    /**
     * 审核标识（1 - 通过，0 - 未通过）
     */
    protected Boolean isAuditSuccess = Boolean.FALSE;

    /**
     * 审核结果
     */
    protected String auditResult = "";

    public AuditEntity() {}

    public AuditEntity(Long id) {
        super(id);
    }

    public Boolean getAuditSuccess() {
        return isAuditSuccess;
    }

    public AuditEntity setAuditSuccess(Boolean auditSuccess) {
        isAuditSuccess = auditSuccess;
        return this;
    }

    public String getAuditResult() {
        return auditResult;
    }

    public AuditEntity setAuditResult(String auditResult) {
        this.auditResult = auditResult;
        return this;
    }
}
