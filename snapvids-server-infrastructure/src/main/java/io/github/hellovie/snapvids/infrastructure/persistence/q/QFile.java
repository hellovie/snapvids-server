package io.github.hellovie.snapvids.infrastructure.persistence.q;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.File;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QFile is a Querydsl query type for File
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QFile extends EntityPathBase<File> {

    private static final long serialVersionUID = 728480388L;

    public static final QFile file = new QFile("file");

    public final QAuditEntity _super = new QAuditEntity(this);

    //inherited
    public final StringPath auditResult = _super.auditResult;

    //inherited
    public final BooleanPath auditSuccess = _super.auditSuccess;

    public final NumberPath<Long> createdById = createNumber("createdById", Long.class);

    public final EnumPath<io.github.hellovie.snapvids.infrastructure.persistence.enums.FileExt> ext = createEnum("ext", io.github.hellovie.snapvids.infrastructure.persistence.enums.FileExt.class);

    public final StringPath fileKey = createString("fileKey");

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final BooleanPath isAuditSuccess = _super.isAuditSuccess;

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final NumberPath<Long> modifiedById = createNumber("modifiedById", Long.class);

    public final StringPath originalName = createString("originalName");

    public final StringPath path = createString("path");

    public final NumberPath<Long> size = createNumber("size", Long.class);

    public final EnumPath<io.github.hellovie.snapvids.infrastructure.persistence.enums.FileState> state = createEnum("state", io.github.hellovie.snapvids.infrastructure.persistence.enums.FileState.class);

    public final EnumPath<io.github.hellovie.snapvids.infrastructure.persistence.enums.FileStorage> storage = createEnum("storage", io.github.hellovie.snapvids.infrastructure.persistence.enums.FileStorage.class);

    public final StringPath storageName = createString("storageName");

    public final EnumPath<io.github.hellovie.snapvids.infrastructure.persistence.enums.FileType> type = createEnum("type", io.github.hellovie.snapvids.infrastructure.persistence.enums.FileType.class);

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcCreated = _super.utcCreated;

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcModified = _super.utcModified;

    public final EnumPath<io.github.hellovie.snapvids.infrastructure.persistence.enums.FileVisibility> visibility = createEnum("visibility", io.github.hellovie.snapvids.infrastructure.persistence.enums.FileVisibility.class);

    public QFile(String variable) {
        super(File.class, forVariable(variable));
    }

    public QFile(Path<? extends File> path) {
        super(path.getType(), path.getMetadata());
    }

    public QFile(PathMetadata metadata) {
        super(File.class, metadata);
    }

}

