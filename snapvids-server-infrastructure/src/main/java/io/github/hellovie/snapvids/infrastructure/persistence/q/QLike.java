package io.github.hellovie.snapvids.infrastructure.persistence.q;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.Like;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QLike is a Querydsl query type for Like
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLike extends EntityPathBase<Like> {

    private static final long serialVersionUID = 728659103L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLike like = new QLike("like1");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QComment comment;

    public final QCreation creation;

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final BooleanPath isLike = createBoolean("isLike");

    public final EnumPath<io.github.hellovie.snapvids.infrastructure.persistence.enums.LikeObjectType> objectType = createEnum("objectType", io.github.hellovie.snapvids.infrastructure.persistence.enums.LikeObjectType.class);

    public final EnumPath<io.github.hellovie.snapvids.infrastructure.persistence.enums.LikeType> type = createEnum("type", io.github.hellovie.snapvids.infrastructure.persistence.enums.LikeType.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcCreated = _super.utcCreated;

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcModified = _super.utcModified;

    public QLike(String variable) {
        this(Like.class, forVariable(variable), INITS);
    }

    public QLike(Path<? extends Like> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLike(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLike(PathMetadata metadata, PathInits inits) {
        this(Like.class, metadata, inits);
    }

    public QLike(Class<? extends Like> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.comment = inits.isInitialized("comment") ? new QComment(forProperty("comment")) : null;
        this.creation = inits.isInitialized("creation") ? new QCreation(forProperty("creation"), inits.get("creation")) : null;
    }

}

