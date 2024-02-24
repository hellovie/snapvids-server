package io.github.hellovie.snapvids.infrastructure.persistence.q;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.Collect;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QCollect is a Querydsl query type for Collect
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCollect extends EntityPathBase<Collect> {

    private static final long serialVersionUID = 1494032162L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCollect collect = new QCollect("collect");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QCreation creationId;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final EnumPath<io.github.hellovie.snapvids.infrastructure.persistence.enums.CollectObjectType> objectType = createEnum("objectType", io.github.hellovie.snapvids.infrastructure.persistence.enums.CollectObjectType.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcCreated = _super.utcCreated;

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcModified = _super.utcModified;

    public QCollect(String variable) {
        this(Collect.class, forVariable(variable), INITS);
    }

    public QCollect(Path<? extends Collect> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCollect(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCollect(PathMetadata metadata, PathInits inits) {
        this(Collect.class, metadata, inits);
    }

    public QCollect(Class<? extends Collect> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.creationId = inits.isInitialized("creationId") ? new QCreation(forProperty("creationId"), inits.get("creationId")) : null;
    }

}

