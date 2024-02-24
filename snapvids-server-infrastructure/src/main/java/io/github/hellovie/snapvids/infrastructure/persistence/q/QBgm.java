package io.github.hellovie.snapvids.infrastructure.persistence.q;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.Bgm;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QBgm is a Querydsl query type for Bgm
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QBgm extends EntityPathBase<Bgm> {

    private static final long serialVersionUID = 993326784L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QBgm bgm = new QBgm("bgm");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Long> authorId = createNumber("authorId", Long.class);

    public final StringPath bgmName = createString("bgmName");

    public final QFile cover;

    public final NumberPath<Integer> duration = createNumber("duration", Integer.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final QFile music;

    public final NumberPath<Integer> useCount = createNumber("useCount", Integer.class);

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcCreated = _super.utcCreated;

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcModified = _super.utcModified;

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public QBgm(String variable) {
        this(Bgm.class, forVariable(variable), INITS);
    }

    public QBgm(Path<? extends Bgm> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QBgm(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QBgm(PathMetadata metadata, PathInits inits) {
        this(Bgm.class, metadata, inits);
    }

    public QBgm(Class<? extends Bgm> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.cover = inits.isInitialized("cover") ? new QFile(forProperty("cover")) : null;
        this.music = inits.isInitialized("music") ? new QFile(forProperty("music")) : null;
    }

}

