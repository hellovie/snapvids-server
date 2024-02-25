package io.github.hellovie.snapvids.infrastructure.persistence.q;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.Role;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.User;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = 728936659L;

    public static final QUser user = new QUser("user");

    public final QBaseEntity _super = new QBaseEntity(this);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final NumberPath<Integer> lastLoginIp = createNumber("lastLoginIp", Integer.class);

    public final DateTimePath<java.sql.Timestamp> lastLoginTime = createDateTime("lastLoginTime", java.sql.Timestamp.class);

    public final StringPath password = createString("password");

    public final StringPath phoneNumber = createString("phoneNumber");

    public final NumberPath<Integer> registerIp = createNumber("registerIp", Integer.class);

    public final DateTimePath<java.sql.Timestamp> registerTime = createDateTime("registerTime", java.sql.Timestamp.class);

    public final ListPath<Role, QRole> roles = this.<Role, QRole>createList("roles", Role.class, QRole.class, PathInits.DIRECT2);

    public final EnumPath<io.github.hellovie.snapvids.infrastructure.persistence.enums.UserState> state = createEnum("state", io.github.hellovie.snapvids.infrastructure.persistence.enums.UserState.class);

    public final StringPath username = createString("username");

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcCreated = _super.utcCreated;

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcModified = _super.utcModified;

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

