package io.github.hellovie.snapvids.infrastructure.persistence.q;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import io.github.hellovie.snapvids.common.enums.ShareFromType;
import io.github.hellovie.snapvids.common.enums.ShareToType;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.Share;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QShare is a Querydsl query type for Share
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QShare extends EntityPathBase<Share> {

    private static final long serialVersionUID = 1120021463L;

    public static final QShare share = new QShare("share");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Long> fromCommentId = createNumber("fromCommentId", Long.class);

    public final NumberPath<Long> fromCreationId = createNumber("fromCreationId", Long.class);

    public final EnumPath<ShareFromType> fromType = createEnum("fromType", ShareFromType.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final EnumPath<ShareToType> toType = createEnum("toType", ShareToType.class);

    public final NumberPath<Long> toUserId = createNumber("toUserId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcCreated = _super.utcCreated;

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcModified = _super.utcModified;

    public QShare(String variable) {
        super(Share.class, forVariable(variable));
    }

    public QShare(Path<? extends Share> path) {
        super(path.getType(), path.getMetadata());
    }

    public QShare(PathMetadata metadata) {
        super(Share.class, metadata);
    }

}

