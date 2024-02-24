package io.github.hellovie.snapvids.infrastructure.persistence.q;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.BooleanPath;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.NumberPath;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.ChunkFile;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QChunkFile is a Querydsl query type for ChunkFile
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QChunkFile extends EntityPathBase<ChunkFile> {

    private static final long serialVersionUID = 1390587265L;

    public static final QChunkFile chunkFile = new QChunkFile("chunkFile");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final NumberPath<Long> chunkSize = createNumber("chunkSize", Long.class);

    public final NumberPath<Integer> currentNum = createNumber("currentNum", Integer.class);

    public final NumberPath<Long> currentSize = createNumber("currentSize", Long.class);

    public final NumberPath<Long> fileId = createNumber("fileId", Long.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final NumberPath<Integer> totalChunks = createNumber("totalChunks", Integer.class);

    public final NumberPath<Long> totalSize = createNumber("totalSize", Long.class);

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcCreated = _super.utcCreated;

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcModified = _super.utcModified;

    public QChunkFile(String variable) {
        super(ChunkFile.class, forVariable(variable));
    }

    public QChunkFile(Path<? extends ChunkFile> path) {
        super(path.getType(), path.getMetadata());
    }

    public QChunkFile(PathMetadata metadata) {
        super(ChunkFile.class, metadata);
    }

}

