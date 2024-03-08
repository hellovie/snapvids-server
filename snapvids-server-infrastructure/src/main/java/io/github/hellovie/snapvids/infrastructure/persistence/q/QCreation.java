package io.github.hellovie.snapvids.infrastructure.persistence.q;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.Creation;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.Tag;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QCreation is a Querydsl query type for Creation
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QCreation extends EntityPathBase<Creation> {

    private static final long serialVersionUID = 1522757607L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QCreation creation = new QCreation("creation");

    public final QAuditEntity _super = new QAuditEntity(this);

    public final StringPath atUserIds = createString("atUserIds");

    //inherited
    public final StringPath auditResult = _super.auditResult;

    //inherited
    public final BooleanPath auditSuccess = _super.auditSuccess;

    public final NumberPath<Long> authorId = createNumber("authorId", Long.class);

    public final QFile bgm;

    public final NumberPath<Integer> bgmVolume = createNumber("bgmVolume", Integer.class);

    public final StringPath caption = createString("caption");

    public final QFile cover;

    public final StringPath creationFileIds = createString("creationFileIds");

    public final StringPath creationVisibilityUserIds = createString("creationVisibilityUserIds");

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final BooleanPath isAuditSuccess = _super.isAuditSuccess;

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final NumberPath<Integer> permissions = createNumber("permissions", Integer.class);

    public final ListPath<Tag, QTag> tags = this.<Tag, QTag>createList("tags", Tag.class, QTag.class, PathInits.DIRECT2);

    public final EnumPath<io.github.hellovie.snapvids.infrastructure.persistence.enums.CreationType> type = createEnum("type", io.github.hellovie.snapvids.infrastructure.persistence.enums.CreationType.class);

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcCreated = _super.utcCreated;

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcModified = _super.utcModified;

    public final NumberPath<Integer> views = createNumber("views", Integer.class);

    public final EnumPath<io.github.hellovie.snapvids.infrastructure.persistence.enums.CreationVisibility> visibility = createEnum("visibility", io.github.hellovie.snapvids.infrastructure.persistence.enums.CreationVisibility.class);

    public final NumberPath<Integer> volume = createNumber("volume", Integer.class);

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public QCreation(String variable) {
        this(Creation.class, forVariable(variable), INITS);
    }

    public QCreation(Path<? extends Creation> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QCreation(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QCreation(PathMetadata metadata, PathInits inits) {
        this(Creation.class, metadata, inits);
    }

    public QCreation(Class<? extends Creation> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.bgm = inits.isInitialized("bgm") ? new QFile(forProperty("bgm")) : null;
        this.cover = inits.isInitialized("cover") ? new QFile(forProperty("cover")) : null;
    }

}

