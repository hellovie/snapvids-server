package io.github.hellovie.snapvids.infrastructure.persistence.q;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.AuditEntity;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QAuditEntity is a Querydsl query type for AuditEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QAuditEntity extends EntityPathBase<AuditEntity> {

    private static final long serialVersionUID = 1570430358L;

    public static final QAuditEntity auditEntity = new QAuditEntity("auditEntity");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath auditResult = createString("auditResult");

    public final BooleanPath auditSuccess = createBoolean("auditSuccess");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final BooleanPath isAuditSuccess = createBoolean("isAuditSuccess");

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcCreated = _super.utcCreated;

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcModified = _super.utcModified;

    public QAuditEntity(String variable) {
        super(AuditEntity.class, forVariable(variable));
    }

    public QAuditEntity(Path<? extends AuditEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAuditEntity(PathMetadata metadata) {
        super(AuditEntity.class, metadata);
    }

}

