package io.github.hellovie.snapvids.infrastructure.persistence.q;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import io.github.hellovie.snapvids.common.enums.FollowType;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.Follow;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QFollow is a Querydsl query type for Follow
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFollow extends EntityPathBase<Follow> {

    private static final long serialVersionUID = -4464967L;

    public static final QFollow follow = new QFollow("follow");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Long> followerId = createNumber("followerId", Long.class);

    public final NumberPath<Long> followingId = createNumber("followingId", Long.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final StringPath remark = createString("remark");

    public final EnumPath<FollowType> type = createEnum("type", FollowType.class);

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcCreated = _super.utcCreated;

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcModified = _super.utcModified;

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public QFollow(String variable) {
        super(Follow.class, forVariable(variable));
    }

    public QFollow(Path<? extends Follow> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFollow(PathMetadata metadata) {
        super(Follow.class, metadata);
    }

}

