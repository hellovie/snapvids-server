package io.github.hellovie.snapvids.infrastructure.persistence.q;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.UserInfo;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QUserInfo is a Querydsl query type for UserInfo
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserInfo extends EntityPathBase<UserInfo> {

    private static final long serialVersionUID = -564467679L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserInfo userInfo = new QUserInfo("userInfo");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final StringPath address = createString("address");

    public final QFile avatar;

    public final DateTimePath<java.sql.Timestamp> birthday = createDateTime("birthday", java.sql.Timestamp.class);

    public final StringPath city = createString("city");

    public final StringPath country = createString("country");

    public final QFile cover;

    public final StringPath description = createString("description");

    public final StringPath firstName = createString("firstName");

    public final EnumPath<io.github.hellovie.snapvids.infrastructure.persistence.enums.Gender> gender = createEnum("gender", io.github.hellovie.snapvids.infrastructure.persistence.enums.Gender.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final StringPath lastName = createString("lastName");

    public final StringPath nickname = createString("nickname");

    public final NumberPath<Integer> privacyLevel = createNumber("privacyLevel", Integer.class);

    public final StringPath state = createString("state");

    public final QUser user;

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcCreated = _super.utcCreated;

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcModified = _super.utcModified;

    public QUserInfo(String variable) {
        this(UserInfo.class, forVariable(variable), INITS);
    }

    public QUserInfo(Path<? extends UserInfo> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserInfo(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserInfo(PathMetadata metadata, PathInits inits) {
        this(UserInfo.class, metadata, inits);
    }

    public QUserInfo(Class<? extends UserInfo> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.avatar = inits.isInitialized("avatar") ? new QFile(forProperty("avatar")) : null;
        this.cover = inits.isInitialized("cover") ? new QFile(forProperty("cover")) : null;
        this.user = inits.isInitialized("user") ? new QUser(forProperty("user")) : null;
    }

}

