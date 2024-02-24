package io.github.hellovie.snapvids.infrastructure.persistence.q;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import io.github.hellovie.snapvids.infrastructure.persistence.entity.Comment;

import javax.annotation.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QComment is a Querydsl query type for Comment
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QComment extends EntityPathBase<Comment> {

    private static final long serialVersionUID = 1494985815L;

    public static final QComment comment = new QComment("comment");

    public final QAuditEntity _super = new QAuditEntity(this);

    public final StringPath atUserIds = createString("atUserIds");

    //inherited
    public final StringPath auditResult = _super.auditResult;

    //inherited
    public final BooleanPath auditSuccess = _super.auditSuccess;

    public final NumberPath<Long> authorId = createNumber("authorId", Long.class);

    public final StringPath content = createString("content");

    public final NumberPath<Long> creationId = createNumber("creationId", Long.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    //inherited
    public final BooleanPath isAuditSuccess = _super.isAuditSuccess;

    //inherited
    public final BooleanPath isDeleted = _super.isDeleted;

    public final BooleanPath isParentComment = createBoolean("isParentComment");

    public final BooleanPath isTop = createBoolean("isTop");

    public final NumberPath<Long> pid = createNumber("pid", Long.class);

    public final NumberPath<Long> replyUserId = createNumber("replyUserId", Long.class);

    public final EnumPath<io.github.hellovie.snapvids.infrastructure.persistence.enums.CommentType> type = createEnum("type", io.github.hellovie.snapvids.infrastructure.persistence.enums.CommentType.class);

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcCreated = _super.utcCreated;

    //inherited
    public final DateTimePath<java.sql.Timestamp> utcModified = _super.utcModified;

    public final NumberPath<Integer> weight = createNumber("weight", Integer.class);

    public QComment(String variable) {
        super(Comment.class, forVariable(variable));
    }

    public QComment(Path<? extends Comment> path) {
        super(path.getType(), path.getMetadata());
    }

    public QComment(PathMetadata metadata) {
        super(Comment.class, metadata);
    }

}

