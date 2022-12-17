package io.github.depromeet.knockknockbackend.domain.group.domain;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import com.querydsl.core.types.dsl.PathInits;
import javax.annotation.processing.Generated;

/** QGroupUser is a Querydsl query type for GroupUser */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QGroupUser extends EntityPathBase<GroupUser> {

    private static final long serialVersionUID = 334318113L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QGroupUser groupUser = new QGroupUser("groupUser");

    public final io.github.depromeet.knockknockbackend.global.database.QBaseTimeEntity _super =
            new io.github.depromeet.knockknockbackend.global.database.QBaseTimeEntity(this);

    // inherited
    public final DateTimePath<java.time.LocalDateTime> createdDate = _super.createdDate;

    public final QGroup group;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isHost = createBoolean("isHost");

    // inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedDate = _super.modifiedDate;

    public final io.github.depromeet.knockknockbackend.domain.user.domain.QUser user;

    public QGroupUser(String variable) {
        this(GroupUser.class, forVariable(variable), INITS);
    }

    public QGroupUser(Path<? extends GroupUser> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QGroupUser(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QGroupUser(PathMetadata metadata, PathInits inits) {
        this(GroupUser.class, metadata, inits);
    }

    public QGroupUser(Class<? extends GroupUser> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.group =
                inits.isInitialized("group")
                        ? new QGroup(forProperty("group"), inits.get("group"))
                        : null;
        this.user =
                inits.isInitialized("user")
                        ? new io.github.depromeet.knockknockbackend.domain.user.domain.QUser(
                                forProperty("user"))
                        : null;
    }
}
